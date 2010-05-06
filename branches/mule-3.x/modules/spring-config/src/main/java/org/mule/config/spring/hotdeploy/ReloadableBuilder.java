/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.hotdeploy;

import org.mule.api.MuleContext;
import org.mule.api.config.ConfigurationException;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.config.ConfigResource;
import org.mule.config.StartupContext;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.context.notification.MuleContextNotification;
import org.mule.context.notification.NotificationException;
import org.mule.module.boot.MuleBootstrapUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class ReloadableBuilder extends SpringXmlConfigurationBuilder
{

    /**
     * A logical root for this application.
     */
    protected static final ClassLoader CLASSLOADER_ROOT = Thread.currentThread().getContextClassLoader();

    protected static final URL[] CLASSPATH_EMPTY = new URL[0];

    protected final transient Log logger = LogFactory.getLog(getClass());

    // TODO multiple resource monitoring
    protected File monitoredResource;

    protected static final int RELOAD_INTERVAL_MS = 3000;

    protected ScheduledExecutorService watchTimer;

    public ReloadableBuilder(ConfigResource[] configResources)
    {
        super(configResources);
    }

    public ReloadableBuilder(String s) throws ConfigurationException
    {
        super(s);
    }


    public ReloadableBuilder(String[] strings) throws ConfigurationException
    {
        super(strings);
    }


    @Override
    public void configure(final MuleContext muleContext) throws ConfigurationException
    {
        final boolean redeploymentEnabled = !StartupContext.get().getStartupOptions().containsKey("production");

        try
        {
            // TODO dup
            final ConfigResource[] allResources;
            if (useDefaultConfigResource)
            {
                allResources = new ConfigResource[configResources.length + 2];
                allResources[0] = new ConfigResource(MULE_SPRING_CONFIG);
                allResources[1] = new ConfigResource(MULE_DEFAULTS_CONFIG);
                System.arraycopy(configResources, 0, allResources, 2, configResources.length);
                // need getUrl().getFile(), otherwise lastModified timestamp always returns 0
                this.monitoredResource = new File(allResources[2].getUrl().getFile());
            }
            else
            {
                allResources = new ConfigResource[configResources.length + 1];
                allResources[0] = new ConfigResource(MULE_SPRING_CONFIG);
                System.arraycopy(configResources, 0, allResources, 1, configResources.length);
                // need getUrl().getFile(), otherwise lastModified timestamp always returns 0
                this.monitoredResource = new File(allResources[1].getUrl().getFile());
            }
            // end dup

            // TODO this is really a job of a deployer and deployment descriptor info
            ClassLoader parent = MuleBootstrapUtils.isStandalone()
                                                        ? new DefaultMuleSharedDomainClassLoader(CLASSLOADER_ROOT)
                                                        : CLASSLOADER_ROOT;
            ClassLoader cl = new MuleApplicationClassLoader(this.monitoredResource, parent);
            Thread.currentThread().setContextClassLoader(cl);



            if (redeploymentEnabled && logger.isInfoEnabled())
            {
                logger.info("Monitoring for hot-reload: " + monitoredResource);
            }

            final FileWatcher watcher = new ConfigFileWatcher(muleContext);

            if (redeploymentEnabled)
            {
                // register a config monitor only after context has started, as it may take some time
                muleContext.registerListener(new MuleContextNotificationListener<MuleContextNotification>() {

                    public void onNotification(MuleContextNotification notification)
                    {
                        final int action = notification.getAction();
                        switch (action)
                        {
                            case MuleContextNotification.CONTEXT_STARTED:
                                System.out.println("ReloadableBuilder.onNotification:: CONTEXT_STARTED");
                                scheduleConfigMonitor(watcher);
                                break;
                            case MuleContextNotification.CONTEXT_STOPPING:
                                System.out.println("ReloadableBuilder.onNotification:: CONTEXT_STOPPING");
                                watchTimer.shutdownNow();
                                muleContext.unregisterListener(this);
                                break;
                        }
                    }
                });
            }

            super.configure(muleContext);
        }
        catch (NotificationException e)
        {
            throw new ConfigurationException(e);
        }
        catch (IOException e)
        {
            throw new ConfigurationException(e);
        }
        //finally
        //{
        //    Thread.currentThread().setContextClassLoader(rootClassloader);
        //}

    }

    protected void scheduleConfigMonitor(FileWatcher watcher)
    {
        final int reloadIntervalMs = RELOAD_INTERVAL_MS;
        // time cancellation handled in the watcher's onChange() callback
        watchTimer = Executors.newSingleThreadScheduledExecutor();
        watchTimer.scheduleWithFixedDelay(watcher, reloadIntervalMs, reloadIntervalMs, TimeUnit.MILLISECONDS);

        if (logger.isInfoEnabled())
        {
            logger.info("Reload interval: " + reloadIntervalMs);
        }
    }

    protected void doConfigure(final MuleContext muleContext) throws Exception
    {
        final ConfigResource[] allResources;
        if (useDefaultConfigResource)
        {
            allResources = new ConfigResource[configResources.length + 2];
            allResources[0] = new ConfigResource(MULE_SPRING_CONFIG);
            allResources[1] = new ConfigResource(MULE_DEFAULTS_CONFIG);
            System.arraycopy(configResources, 0, allResources, 2, configResources.length);
        }
        else
        {
            allResources = new ConfigResource[configResources.length + 1];
            allResources[0] = new ConfigResource(MULE_SPRING_CONFIG);
            System.arraycopy(configResources, 0, allResources, 1, configResources.length);
        }

        createSpringRegistry(muleContext, createApplicationContext(muleContext, allResources));

    }

    protected class ConfigFileWatcher extends FileWatcher
    {

        private final MuleContext muleContext;

        public ConfigFileWatcher(MuleContext muleContext)
        {
            super(ReloadableBuilder.this.monitoredResource);
            this.muleContext = muleContext;
        }

        protected synchronized void onChange(File file)
        {
            if (logger.isInfoEnabled())
            {
                logger.info("================== Reloading " + file);
            }


            try
            {
                muleContext.dispose();
                Thread.currentThread().setContextClassLoader(null);
                // TODO this is really a job of a deployer and deployment descriptor info
                // TODO I don't think shared domains can be safely redeployed, this will probably be removed
                ClassLoader parent = MuleBootstrapUtils.isStandalone()
                                     ? new DefaultMuleSharedDomainClassLoader(CLASSLOADER_ROOT)
                                     : CLASSLOADER_ROOT;
                ClassLoader cl = new MuleApplicationClassLoader(monitoredResource, parent);
                Thread.currentThread().setContextClassLoader(cl);

                DefaultMuleContextFactory f = new DefaultMuleContextFactory();
                MuleContext newContext = f.createMuleContext(ReloadableBuilder.this);
                newContext.start();
            }
            catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
            //finally
            //{
            //    Thread.currentThread().setContextClassLoader(rootClassloader);
            //}

        }
    }
}

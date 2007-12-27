/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck;

import org.mule.MuleServer;
import org.mule.RegistryContext;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.tck.testmodels.mule.TestConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.FileUtils;
import org.mule.util.MuleUrlStreamHandlerFactory;
import org.mule.util.StringMessageUtils;
import org.mule.util.StringUtils;
import org.mule.util.SystemUtils;
import org.mule.util.concurrent.Latch;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;
import junit.framework.TestResult;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>AbstractMuleTestCase</code> is a base class for Mule testcases. This
 * implementation provides services to test code for creating mock and test objects.
 */
public abstract class AbstractMuleTestCase extends TestCase implements TestCaseWatchdogTimeoutHandler
{

    /**
     * Top-level directories under <code>.mule</code> which are not deleted on each
     * test case recycle. This is required, e.g. to play nice with transaction manager
     * recovery service object store.
     */
    public static final String[] IGNORED_DOT_MULE_DIRS = new String[]{"transaction-log"};

    protected static UMOManagementContext managementContext;

    /**
     * This flag controls whether the text boxes will be logged when starting each test case.
     */
    private static final boolean verbose;

    // A Map of test case extension objects. JUnit creates a new TestCase instance for
    // every method, so we need to record metainfo outside the test.
    private static final Map testInfos = Collections.synchronizedMap(new HashMap());

    // A logger that should be suitable for most test cases.
    protected final transient Log logger = LogFactory.getLog(this.getClass());

    /**
     * Start the ManagementContext once it's configured (defaults to false for AbstractMuleTestCase, true for FunctionalTestCase).
     */
    private boolean startContext = false;

    // Should be set to a string message describing any prerequisites not met.
    private boolean offline = "true".equalsIgnoreCase(System.getProperty("org.mule.offline"));

    // Barks if the test exceeds its time limit
    private TestCaseWatchdog watchdog;

    static
    {
        String muleOpts = SystemUtils.getenv("MULE_TEST_OPTS");
        if (StringUtils.isNotBlank(muleOpts))
        {
            Map parsedOpts = SystemUtils.parsePropertyDefinitions(muleOpts);
            String optVerbose = (String) parsedOpts.get("mule.verbose");
            verbose = Boolean.valueOf(optVerbose).booleanValue();
        }
        else
        {
            // per default, revert to the old behaviour
            verbose = true;
        }

        // register the custom UrlStreamHandlerFactory.
        MuleUrlStreamHandlerFactory.installUrlStreamHandlerFactory();
    }

    /**
     * Convenient test message for unit testing.
     */
    public static final String TEST_MESSAGE = "Test Message";

    /**
     * Default timeout for multithreaded tests (using CountDownLatch, WaitableBoolean, etc.),
     * in milliseconds.  The higher this value, the more reliable the test will be, so it
     * should be set high for Continuous Integration.  However, this can waste time during
     * day-to-day development cycles, so you may want to temporarily lower it while debugging.
     */
    public static final long LOCK_TIMEOUT = 30000;

    /**
     * Use this as a semaphore to the unit test to indicate when a callback has successfully been called.
     */
    protected Latch callbackCalled;

    public AbstractMuleTestCase()
    {
        super();

        TestInfo info = (TestInfo) testInfos.get(getClass().getName());
        if (info == null)
        {
            info = this.createTestInfo();
            testInfos.put(getClass().getName(), info);
        }
        this.registerTestMethod();
    }

    protected void registerTestMethod()
    {
        if (this.getName() != null)
        {
            this.getTestInfo().incTestCount(getName());
        }
    }

    public void setName(String name)
    {
        super.setName(name);
        registerTestMethod();
    }

    protected TestInfo createTestInfo()
    {
        return new TestInfo(this);
    }

    protected TestInfo getTestInfo()
    {
        return (TestInfo) testInfos.get(this.getClass().getName());
    }

    private void clearInfo()
    {
        testInfos.remove(this.getClass().getName());
    }

    public String getName()
    {
        if (verbose && super.getName() != null)
        {
            return super.getName().substring(4).replaceAll("([A-Z])", " $1").toLowerCase() + " ";
        }
        return super.getName();
    }

    public void run(TestResult result)
    {
        if (this.isExcluded())
        {
            if (verbose)
            {
                logger.info(this.getClass().getName() + " excluded");
            }
            return;
        }

        if (this.isDisabledInThisEnvironment())
        {
            if (verbose)
            {
                logger.info(this.getClass().getName() + " disabled");
            }
            return;
        }

        super.run(result);
    }

    /**
     * Shamelessly copy from Spring's ConditionalTestCase so in MULE-2.0 we can extend
     * this class from ConditionalTestCase.
     * 
     * Subclasses can override <code>isDisabledInThisEnvironment</code> to skip a single test.
     */
    public void runBare() throws Throwable
    {
        // getName will return the name of the method being run. Use the real JUnit implementation,
        // this class has a different implementation
        if (this.isDisabledInThisEnvironment(super.getName()))
        {
            logger.warn(this.getClass().getName() + "." + super.getName() + " disabled in this environment");
            return;
        }

        // Let JUnit handle execution
        super.runBare();
    }

    /**
     * Subclasses can override this method to skip the execution of the entire test class.
     *
     * @return <code>true</code> if the test class should not be run.
     */
    protected boolean isDisabledInThisEnvironment()
    {
        return false;
    }

    /**
     * Indicates whether this test has been explicitly disabled through the configuration
     * file loaded by TestInfo.
     *
     * @return whether the test has been explicitly disabled
     */
    protected boolean isExcluded()
    {
        return getTestInfo().isExcluded();
    }

    /**
     * Should this test run?
     *
     * @param testMethodName name of the test method
     * @return whether the test should execute in the current envionment
     */
    protected boolean isDisabledInThisEnvironment(String testMethodName)
    {
        return false;
    }

    public boolean isOffline(String method)
    {
        if (offline)
        {
            logger.warn(StringMessageUtils.getBoilerPlate(
                    "Working offline cannot run test: " + method, '=', 80));
        }
        return offline;
    }

    protected boolean isDisposeManagerPerSuite()
    {
        return getTestInfo().isDisposeManagerPerSuite();
    }

    protected void setDisposeManagerPerSuite(boolean val)
    {
        getTestInfo().setDisposeManagerPerSuite(val);
    }

    protected TestCaseWatchdog createWatchdog()
    {
        return new TestCaseWatchdog(10, TimeUnit.MINUTES, this);
    }

    public void handleTimeout(long timeout, TimeUnit unit)
    {
        logger.fatal("Timeout of " + unit.toMillis(timeout) + "ms exceeded - exiting VM!");
        Runtime.getRuntime().halt(1);
    }

    protected final void setUp() throws Exception
    {
        // start a watchdog thread
        watchdog = createWatchdog();
        watchdog.start();

        if (verbose)
        {
            System.out.println(StringMessageUtils.getBoilerPlate("Testing: " + toString(), '=', 80));
        }

        try
        {
            if (getTestInfo().getRunCount() == 0)
            {
                if (getTestInfo().isDisposeManagerPerSuite())
                {
                    // We dispose here jut in case
                    disposeManager();
                }
                suitePreSetUp();
            }
            if (!getTestInfo().isDisposeManagerPerSuite())
            {
                // We dispose here just in case
                disposeManager();
            }

            managementContext = createManagementContext();
            MuleServer.setManagementContext(managementContext);
//            if(!managementContext.getRegistry().isInitialised())
//            {
//                managementContext.getRegistry().initialise();
//            }
            if (isStartContext() && managementContext.isStarted() == false)
            {
                // TODO MULE-1988
                managementContext.start();
            }

            doSetUp();
        }
        catch (Exception e)
        {
            getTestInfo().incRunCount();
            throw e;
        }
    }

    protected UMOManagementContext createManagementContext() throws Exception
    {
        // Should we set up the manager for every method?
        UMOManagementContext context;
        if (getTestInfo().isDisposeManagerPerSuite() && managementContext != null)
        {
            context = managementContext;
        }
        else
        {
            ConfigurationBuilder builder = getBuilder();
            context = builder.configure(getConfigurationResources(), getStartUpProperties());
        }
        return context;
    }

    protected ConfigurationBuilder getBuilder() throws Exception
    {
        return new MuleXmlConfigurationBuilder();
    }

    protected String getConfigurationResources()
    {
        return StringUtils.EMPTY;
    }

    protected Properties getStartUpProperties()
    {
        return null;
    }

    /**
     * Run <strong>before</strong> any testcase setup.
     */
    protected void suitePreSetUp() throws Exception
    {
        // nothing to do
    }

    /**
     * Run <strong>after</strong> all testcase teardowns.
     */
    protected void suitePostTearDown() throws Exception
    {
        // nothing to do
    }

    protected final void tearDown() throws Exception
    {
        try
        {
            doTearDown();

            if (!getTestInfo().isDisposeManagerPerSuite())
            {
                disposeManager();
            }
        }
        finally
        {
            try
            {
                getTestInfo().incRunCount();
                if (getTestInfo().getRunCount() == getTestInfo().getTestCount())
                {
                    try
                    {
                        suitePostTearDown();
                    }
                    finally
                    {
                        clearInfo();
                        disposeManager();
                    }
                }
            }
            finally
            {
                // remove the watchdog thread in any case
                watchdog.cancel();
            }
        }
    }

    protected void disposeManager()
    {
        try
        {
            if (managementContext != null && !(managementContext.isDisposed() || managementContext.isDisposing()))
            {
                managementContext.dispose();

                if (RegistryContext.getRegistry() != null)
                {
                    final String workingDir = RegistryContext.getConfiguration().getWorkingDirectory();
                    // do not delete TM recovery object store, everything else is good to go
                    FileUtils.deleteTree(FileUtils.newFile(workingDir), IGNORED_DOT_MULE_DIRS);

                    RegistryContext.getRegistry().dispose();
                }
            }
            FileUtils.deleteTree(FileUtils.newFile("./ActiveMQ"));
        }
        finally
        {
            managementContext = null;
            RegistryContext.setRegistry(null);
        }
    }

    protected void doSetUp() throws Exception
    {
        // template method
    }

    protected void doTearDown() throws Exception
    {
        // template method
    }

    public static UMOEndpoint getTestEndpoint(String name, String type) throws Exception
    {
        return MuleTestUtils.getTestEndpoint(name, type, managementContext);
    }

    public static UMOEvent getTestEvent(Object data, UMOComponent component) throws Exception
    {
        return MuleTestUtils.getTestEvent(data, component, managementContext);
    }

    public static UMOEvent getTestEvent(Object data) throws Exception
    {
        return MuleTestUtils.getTestEvent(data, managementContext);
    }

    public static UMOEventContext getTestEventContext(Object data) throws Exception
    {
        return MuleTestUtils.getTestEventContext(data, managementContext);
    }

    public static UMOTransformer getTestTransformer() throws Exception
    {
        return MuleTestUtils.getTestTransformer();
    }

    public static UMOEvent getTestEvent(Object data, UMOImmutableEndpoint endpoint) throws Exception
    {
        return MuleTestUtils.getTestEvent(data, endpoint, managementContext);
    }

    public static UMOEvent getTestEvent(Object data, UMOComponent component, UMOImmutableEndpoint endpoint)
        throws Exception
    {
        return MuleTestUtils.getTestEvent(data, component, endpoint, managementContext);
    }

    public static UMOSession getTestSession(UMOComponent component)
    {
        return MuleTestUtils.getTestSession(component);
    }

    public static TestConnector getTestConnector() throws Exception
    {
        return MuleTestUtils.getTestConnector(managementContext);
    }

    public static UMOComponent getTestComponent() throws Exception
    {
        return MuleTestUtils.getTestComponent(managementContext);
    }

    public static UMOComponent getTestComponent(String name, Class clazz) throws Exception
    {
        return MuleTestUtils.getTestComponent(name, clazz, managementContext);
    }

    public static UMOComponent getTestComponent(String name, Class clazz, Map props) throws Exception
    {
        return MuleTestUtils.getTestComponent(name, clazz, props, managementContext);
    }

    public static class TestInfo
    {
        /**
         * Whether to dispose the manager after every method or once all tests for
         * the class have run
         */
        private final String name;
        private boolean disposeManagerPerSuite = false;
        private boolean excluded = false;
        private volatile int testCount = 0;
        private volatile int runCount = 0;
        // @GuardedBy(this)
        private Set registeredTestMethod = new HashSet();

        // this is a shorter version of the snippet from:
        // http://www.davidflanagan.com/blog/2005_06.html#000060
        // (see comments; DF's "manual" version works fine too)
        public static URL getClassPathRoot(Class clazz)
        {
            CodeSource cs = clazz.getProtectionDomain().getCodeSource();
            return (cs != null ? cs.getLocation() : null);
        }

        public TestInfo(TestCase test)
        {
            this.name = test.getClass().getName();

            // load test exclusions
            try
            {
                // We find the physical classpath root URL of the test class and
                // use that to find the correct resource. Works fine everywhere,
                // regardless of classloaders. See MULE-2414
                URL[] urls = new URL[]{getClassPathRoot(test.getClass())};
                URL fileUrl = new URLClassLoader(urls).getResource("mule-test-exclusions.txt");

                if (fileUrl != null)
                {
                    // this iterates over all lines in the exclusion file
                    Iterator lines = FileUtils.lineIterator(FileUtils.newFile(fileUrl.getFile()));

                    // ..and this finds non-comments that match the test case name
                    excluded = IteratorUtils.filteredIterator(lines, new Predicate()
                    {
                        public boolean evaluate(Object object)
                        {
                            return StringUtils.equals(name, StringUtils.trimToEmpty((String) object));
                        }
                    }).hasNext();
                }
            }
            catch (IOException ioex)
            {
                // ignore
            }
        }

        public int getTestCount()
        {
            return testCount;
        }

        public synchronized void incTestCount(String name)
        {
            if (!registeredTestMethod.contains(name))
            {
                testCount++;
                registeredTestMethod.add(name);
            }
        }

        public int getRunCount()
        {
            return runCount;
        }

        public void incRunCount()
        {
            runCount++;
        }

        public String getName()
        {
            return name;
        }

        public boolean isDisposeManagerPerSuite()
        {
            return disposeManagerPerSuite;
        }

        public void setDisposeManagerPerSuite(boolean disposeManagerPerSuite)
        {
            this.disposeManagerPerSuite = disposeManagerPerSuite;
        }

        public boolean isExcluded()
        {
            return excluded;
        }

        public synchronized String toString()
        {
            StringBuffer buf = new StringBuffer();
            return buf.append(name).append(", (").append(runCount).append(" / ").append(testCount).append(
                    ") tests run, disposePerSuite=").append(disposeManagerPerSuite).toString();
        }
    }

    protected boolean isStartContext()
    {
        return startContext;
    }

    protected void setStartContext(boolean startContext)
    {
        this.startContext = startContext;
    }
}

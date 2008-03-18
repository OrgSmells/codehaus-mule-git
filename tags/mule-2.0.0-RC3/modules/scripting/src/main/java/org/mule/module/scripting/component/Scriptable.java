/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.scripting.component;

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.LifecycleTransitionResult;
import org.mule.config.i18n.CoreMessages;
import org.mule.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A JSR 223 Script service. Allows any JSR 223 compliant script engines such as
 * javaScript, Groovy or Rhino to be embedded as Mule components.
 */
public class Scriptable implements Initialisable
{

    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    private String scriptText;
    private String scriptFile;
    private Reader script;

    private CompiledScript compiledScript;
    private ScriptEngine scriptEngine;
    private String scriptEngineName;

    public LifecycleTransitionResult initialise() throws InitialisationException
    {
        if (scriptEngine == null)
        {
            if (compiledScript == null)
            {
                if (scriptEngineName != null)
                {
                    scriptEngine = createScriptEngineByName(scriptEngineName);
                }
                else if (scriptFile != null)
                {
                    int i = scriptFile.lastIndexOf(".");
                    if (i > -1)
                    {
                        logger.info("Script Engine name not set. Guessing by file extension.");
                        scriptEngine = createScriptEngineByExtension(scriptFile.substring(i + 1));
                        if(scriptEngine != null)
                        {
                            setScriptEngineName(scriptEngine.getFactory().getEngineName());
                        }
                    }
                }
                if (scriptEngine == null)
                {
                    throw new InitialisationException(
                        CoreMessages.propertiesNotSet("scriptEngine, scriptEngineName, compiledScript"), 
                        this);
                }
            }
            else
            {
                scriptEngine = compiledScript.getEngine();
            }
        }

        if (compiledScript == null)
        {
            if (script == null)
            {
                if (scriptText == null && scriptFile == null)
                {
                    throw new InitialisationException(
                        CoreMessages.propertiesNotSet("scriptText, scriptFile"), this);
                }
                else if (scriptText != null)
                {
                    script = new StringReader(scriptText);
                }
                else
                {
                    InputStream is;
                    try
                    {
                        is = IOUtils.getResourceAsStream(scriptFile, getClass());
                        script = new InputStreamReader(is);
                    }
                    catch (IOException e)
                    {
                        throw new InitialisationException(
                            CoreMessages.cannotLoadFromClasspath(scriptFile), e, this);
                    }
                }
            }
            try
            {
                compiledScript = compileScript(script);
            }
            catch (ScriptException e)
            {
                throw new InitialisationException(e, this);
            }
        }
        return LifecycleTransitionResult.OK;
    }

    public ScriptEngine getScriptEngine()
    {
        return scriptEngine;
    }

    public void setScriptEngine(ScriptEngine scriptEngine)
    {
        this.scriptEngine = scriptEngine;
    }

    public CompiledScript getCompiledScript()
    {
        return compiledScript;
    }

    public void setCompiledScript(CompiledScript compiledScript)
    {
        this.compiledScript = compiledScript;
    }

    public String getScriptText()
    {
        return scriptText;
    }

    public void setScriptText(String scriptText)
    {
        this.scriptText = scriptText;
    }

    public String getScriptFile()
    {
        return scriptFile;
    }

    public void setScriptFile(String scriptFile)
    {
        this.scriptFile = scriptFile;
    }

    public void setScriptEngineName(String scriptEngineName)
    {
        this.scriptEngineName = scriptEngineName;
    }

    public String getScriptEngineName()
    {
        return scriptEngineName;
    }

    protected CompiledScript compileScript(Compilable compilable, Reader scriptReader) throws ScriptException
    {
        return compilable.compile(scriptReader);
    }

    protected CompiledScript compileScript(Reader scriptReader) throws ScriptException
    {
        if (scriptEngine instanceof Compilable)
        {
            Compilable compilable = (Compilable)scriptEngine;
            return compileScript(compilable, scriptReader);
        }
        return null;
    }

    protected CompiledScript compileScript(Compilable compilable) throws ScriptException
    {
        return compileScript(compilable, script);
    }

    protected Object evaluteScript(Bindings bindings) throws ScriptException
    {
        return scriptEngine.eval(scriptText, bindings);
    }

    public Object runScript(Bindings bindings) throws ScriptException
    {
        Object result;
        if (compiledScript != null)
        {
            result = compiledScript.eval(bindings);
        }
        else
        {
            result = evaluteScript(bindings);
        }
        return result;
    }

    public Object runScript(CompiledScript compiledScript, Bindings bindings) throws ScriptException
    {
        Object result = null;
        if (compiledScript != null)
        {
            result = compiledScript.eval(bindings);
        }
        return result;
    }

    protected ScriptEngine createScriptEngineByName(String name)
    {
        return new ScriptEngineManager().getEngineByName(name);
    }

    protected ScriptEngine createScriptEngineByExtension(String ext)
    {
        return new ScriptEngineManager().getEngineByExtension(ext);
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemUtils extends org.apache.commons.lang.SystemUtils
{
    protected static transient Log logger = LogFactory.getLog(SystemUtils.class);

    // bash prepends: declare -x
    // zsh prepends: typeset -x
    private static String[] UNIX_ENV_PREFIXES = new String[]{"declare -", "typeset -"};

    /**
     * Get the operating system environment variables.
     * This should work for Windows and Linux.
     * 
     * @return Map<String, String> or an empty map if there was an error.
     */
    public static synchronized Map getenv()
    {
        Map env = Collections./*<String, String>*/EMPTY_MAP;

        try {
            if (SystemUtils.IS_JAVA_1_5) {
                // the following runaround is necessary since we still want to compile on JDK 1.4
                Method envMethod = System.class.getMethod("getenv", ArrayUtils.EMPTY_CLASS_ARRAY);
                env = (Map)envMethod.invoke(System.class, (Class[])null);
            }
            else {
                // fallback
                env = getenvJDK14();
            }
        }
        catch (Exception ex) {
            logger.error("Could not access OS environment: ", ex);
        }

        return env;
    }

    private static Map getenvJDK14() throws Exception
    {
        Map env = new HashMap();
        Process process = null;

        try {
            boolean isUnix = true;
            String command;

            if (SystemUtils.IS_OS_WINDOWS) {
                command = "cmd /c set";
                isUnix = false;
            }
            else {
                command = "env";
            }

            process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                for (int prefix = 0; prefix < UNIX_ENV_PREFIXES.length; prefix++) {
                    if (line.startsWith(UNIX_ENV_PREFIXES[prefix])) {
                        line = line.substring(UNIX_ENV_PREFIXES[prefix].length());
                    }
                }

                int index = -1;
                if ((index = line.indexOf('=')) > -1) {
                    String key = line.substring(0, index).trim();
                    String value = line.substring(index + 1).trim();
                    // remove quotes, if any
                    if (isUnix && value.length() > 1
                                    && (value.startsWith("\"") || value.startsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }
                    env.put(key, value);
                }
                else {
                    env.put(line, StringUtils.EMPTY);
                }
            }
        }
        catch (Exception e) {
            throw e; // bubble up
        }
        finally {
            if (process != null) {
                process.destroy();
            }
        }

        return env;
    }
    
    public static String getenv(String name)
    {
        return (String)SystemUtils.getenv().get(name);
    }

    public static boolean isSunJDK()
    {
        return SystemUtils.JAVA_VM_VENDOR.toUpperCase().indexOf("SUN") != -1;
    }

    public static boolean isIbmJDK()
    {
        return SystemUtils.JAVA_VM_VENDOR.toUpperCase().indexOf("IBM") != -1;
    }

}

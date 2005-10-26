/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 *
 */

package org.mule.test.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.mule.util.StringMessageHelper;

/**
 * <code>StringMessageHelperTest</code> test the methods of the
 * StringMessageHelper formatting class
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class StringMessageHelperTestCase extends TestCase
{

    /**
     * 
     */
    public StringMessageHelperTestCase()
    {
        super();
    }

    public void testGetObjectValue() throws Exception
    {
        Object test = "Oscar";
        Object result = StringMessageHelper.getObjectValue(test);
        assertEquals("Oscar", result);

        test = getClass();
        result = StringMessageHelper.getObjectValue(test);
        assertEquals(getClass().getName(), result);

        test = new TestObject("Ernie");
        result = StringMessageHelper.getObjectValue(test);
        assertEquals(test.toString(), result);

        test = new AnotherTestObject("Bert");
        result = StringMessageHelper.getObjectValue(test);
        assertEquals("Bert", result);

    }

    public void testFormattedString() throws Exception
    {
        String result;
        String msg1 = "There is not substitution here";

        result = StringMessageHelper.getFormattedMessage(msg1, null);
        assertEquals(msg1, result);

        result = StringMessageHelper.getFormattedMessage(msg1, new Object[] {});
        assertEquals(msg1, result);

        String msg2 = "There should be a variable {0}, {1} and {2}";
        result = StringMessageHelper.getFormattedMessage(msg2, new Object[] { "here", "there", "everywhere" });
        assertEquals("There should be a variable here, there and everywhere", result);
    }

    public void testBoilerPlate() throws Exception
    {
        List msgs = new ArrayList();
        msgs.add("This");
        msgs.add("is a");
        msgs.add("Boiler Plate");

        String plate = StringMessageHelper.getBoilerPlate(msgs, '*', 12);
        assertEquals((char) Character.LINE_SEPARATOR + "************" + (char) Character.LINE_SEPARATOR + "* This     *" +
                     (char) Character.LINE_SEPARATOR + "* is a     *" + (char) Character.LINE_SEPARATOR + "* Boiler   *" +
                     (char) Character.LINE_SEPARATOR + "* Plate    *" + (char) Character.LINE_SEPARATOR + "************", plate);

    }

    public void testBoilerPlate2() throws Exception
    {
        List msgs = new ArrayList();
        msgs.add("This");
        msgs.add("is a");
        msgs.add("Boiler Plate Message that should get wrapped to the next line if it is working properly");

        String plate = StringMessageHelper.getBoilerPlate(msgs, '*', 12);
        assertEquals((char) Character.LINE_SEPARATOR + "************" + (char) Character.LINE_SEPARATOR +
                     "* This     *" + (char) Character.LINE_SEPARATOR + "* is a     *" + (char) Character.LINE_SEPARATOR +
                     "* Boiler   *" + (char) Character.LINE_SEPARATOR + "* Plate    *" + (char) Character.LINE_SEPARATOR +
                     "* Message  *" + (char) Character.LINE_SEPARATOR + "* that     *" + (char) Character.LINE_SEPARATOR +
                     "* should   *" + (char) Character.LINE_SEPARATOR + "* get      *" + (char) Character.LINE_SEPARATOR +
                     "* wrapped  *" + (char) Character.LINE_SEPARATOR + "* to the   *" + (char) Character.LINE_SEPARATOR +
                     "* next     *" + (char) Character.LINE_SEPARATOR + "* line if  *" + (char) Character.LINE_SEPARATOR +
                     "* it is    *" + (char) Character.LINE_SEPARATOR + "* working  *" + (char) Character.LINE_SEPARATOR +
                     "* properly *" + (char) Character.LINE_SEPARATOR + "************",
                     plate);
    }

    public void testTruncate()
    {
        String msg = "this is a test message for truncating";
        String result = StringMessageHelper.truncate(msg, 100, true);
        assertEquals(msg, result);

        result = StringMessageHelper.truncate(msg, 10, false);
        assertEquals("this is a ...", result);

        result = StringMessageHelper.truncate(msg, 10, true);
        assertEquals("this is a ...[10 of 37]", result);
    }

    private class TestObject
    {
        private String name;

        public TestObject(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }

    private class AnotherTestObject extends TestObject
    {
        public AnotherTestObject(String name)
        {
            super(name);
        }

        public String toString()
        {
            return getName();
        }
    }

}

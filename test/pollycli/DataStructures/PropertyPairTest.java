/*
 * The MIT License
 *
 * Copyright 2018 Jacob Boone.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pollycli.DataStructures;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jacob Boone
 */
public class PropertyPairTest {
    
    public PropertyPairTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getTarget method, of class PropertyPair.
     */
    @Test
    public void testGetTarget() {
        System.out.println("getTarget");
        PropertyPair instance = new PropertyPair("AAA", "BBB");
        String expResult = "AAA";
        String result = instance.getTarget();
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class PropertyPair.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        PropertyPair instance = new PropertyPair("BBB", "AAA");
        String expResult = "AAA";
        String result = instance.getData();
        assertEquals(expResult, result);
    }

    /**
     * Test of setData method, of class PropertyPair.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        String data = "jalepano";
        PropertyPair instance = new PropertyPair("123", "BBB");
        instance.setData(data);
        assertTrue(instance.getData().equals(data));
        assertFalse(instance.getTarget().equals("BBB"));
    }

    /**
     * Test of setTarget method, of class PropertyPair.
     */
    @Test
    public void testSetTarget() {
        System.out.println("setTarget");
        String target = "ABC";
        PropertyPair instance = new PropertyPair("BBB", "123");
        instance.setTarget(target);
        assertTrue(instance.getTarget().equals(target));
        assertFalse(instance.getTarget().equals("BBB"));
    }
    
}

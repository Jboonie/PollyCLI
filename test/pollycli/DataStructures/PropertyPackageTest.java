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

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jacob Boone
 */
public class PropertyPackageTest {
    
    private PropertyPair testPair;
    private PropertyPackage testPack;
    
    public PropertyPackageTest() {
        testPair = new PropertyPair("A", "B");
        testPack = new PropertyPackage();
        testPack.add(testPair);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of add method, of class PropertyPackage.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        PropertyPair pair = testPair;
        PropertyPackage instance = new PropertyPackage();
        instance.add(pair);
        assertEquals(instance.get(0), testPack.get(0));
    }

    /**
     * Test of remove method, of class PropertyPackage.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        PropertyPair pair = testPair;
        PropertyPackage instance = new PropertyPackage();
        instance.add(pair);
        
        assertEquals(instance.get(0), testPack.get(0));
        instance.remove(pair);
        assertTrue(instance.getPairs().size() < testPack.getPairs().size());
    }

    /**
     * Test of getPairs method, of class PropertyPackage.
     */
    @Test
    public void testGetPairs() {
        System.out.println("getPairs");
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        ArrayList<PropertyPair> expResult = new ArrayList<>();
        expResult.add(testPair);
        ArrayList<PropertyPair> result = instance.getPairs();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class PropertyPackage.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int i = 0;
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        PropertyPair expResult = testPair;
        PropertyPair result = instance.get(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of size method, of class PropertyPackage.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        int expResult = 1;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of set method, of class PropertyPackage.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        int i = 0;
        PropertyPair pair = new PropertyPair("ADAM", "BOY");
        PropertyPackage instance = new PropertyPackage();
        instance.add(pair);
        instance.set(i, testPair);
        assertEquals(testPair, instance.get(i));
    }

    /**
     * Test of hasSetting method, of class PropertyPackage.
     */
    @Test
    public void testHasSetting() {
        System.out.println("hasSetting");
        String str = testPair.getTarget();
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        boolean expResult = true;
        boolean result = instance.hasSetting(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class PropertyPackage.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        String str = testPair.getData();
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        boolean expResult = true;
        boolean result = instance.contains(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of getByTarget method, of class PropertyPackage.
     */
    @Test
    public void testGetByTarget() {
        System.out.println("getByTarget");
        String str = testPair.getTarget();
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        PropertyPair expResult = testPair;
        PropertyPair result = instance.getByTarget(str);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getByData method, of class PropertyPackage.
     */
    @Test
    public void testGetByData() {
        System.out.println("getByData");
        String str = testPair.getData();
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        PropertyPair expResult = testPair;
        PropertyPair result = instance.getByData(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of setByTarget method, of class PropertyPackage.
     */
    @Test
    public void testSetByTarget() {
        System.out.println("setByTarget");
        PropertyPair pair = new PropertyPair(testPair.getTarget(), "Y");
        PropertyPackage instance = new PropertyPackage();
        instance.add(testPair);
        instance.setByTarget(pair);
        
        PropertyPair result = instance.get(0);
        
        assertTrue(result.getData().equals(pair.getData()));
        //fail(result.getData() + " Vs " + pair.getData());
    }
    
}

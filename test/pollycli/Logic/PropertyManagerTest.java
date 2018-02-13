/*
 * The MIT License
 *
 * Copyright 2018 Jacob Boone #000752305.
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
package pollycli.Logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pollycli.DataStructures.PropertyPackage;
import pollycli.DataStructures.PropertyPair;

/**
 *
 * @author Jacob Boone #000752305
 */
public class PropertyManagerTest {
    
    private String fileName;
    private String fileContents;
    private File file;
    private PropertyPair pair;
    
    public PropertyManagerTest() throws FileNotFoundException {
        fileName = "test.txt";
        fileContents = "NARRATOR=Nicole";
        file = new File(fileName);
        PrintWriter pw = new PrintWriter(file);
        pw.println(fileContents);
        pw.close();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of readProperties method, of class PropertyManager.
     */
    @Test
    public void testReadProperties() {
        System.out.println("readProperties");
        PropertyManager instance = new PropertyManager(file.getAbsolutePath());
        instance.readProperties();
        
        PropertyPackage pack = instance.getProperties();
        
        assertTrue(pack.get(0).getData().equals("Nicole"));
        assertTrue(pack.get(0).getTarget().equals("NARRATOR"));
    }

    /**
     * Test of writeProperties method, of class PropertyManager.
     */
    @Test
    public void testWriteProperties() {
        System.out.println("writeProperties");
        PropertyManager instance = new PropertyManager(file.getAbsolutePath());
        instance.addProperty(new PropertyPair("A", "B"));
        instance.writeProperties();
        PropertyPackage pack = instance.getProperties();
        
        assertTrue(pack.get(1).getData().equals("B"));
        assertTrue(pack.get(1).getTarget().equals("A"));
    }

    /**
     * Test of addProperty method, of class PropertyManager.
     */
    @Test
    public void testAddProperty() {
        System.out.println("addProperty");
        PropertyManager instance = new PropertyManager(file.getAbsolutePath());
        instance.addProperty(new PropertyPair("1", "2"));
        PropertyPackage pack = instance.getProperties();
        
        assertTrue(pack.get(1).getData().equals("2"));
        assertTrue(pack.get(1).getTarget().equals("1"));
    }

    /**
     * Test of setPackage method, of class PropertyManager.
     */
    @Test
    public void testSetPackage() {
        System.out.println("setPackage");
        PropertyPackage newPack = new PropertyPackage();
        newPack.add(new PropertyPair("A", "B"));
        
        
        PropertyManager instance = new PropertyManager(file.getAbsolutePath());
        instance.readProperties();
        instance.setPackage(newPack);
        
        PropertyPackage testPack = instance.getProperties();
        
        assertTrue(testPack.get(0).getData().equals("B"));
        assertTrue(testPack.get(0).getTarget().equals("A"));
    }

    /**
     * Test of getProperties method, of class PropertyManager.
     */
    @Test
    public void testGetProperties() {
        System.out.println("getProperties");
        PropertyManager instance = new PropertyManager(file.getAbsolutePath());
        PropertyPackage expResult = new PropertyPackage();
        expResult.add(new PropertyPair("NARRATOR", "Nicole"));
        
        instance.getProperties();
        PropertyPackage oldPack = instance.getProperties();
        
        assertTrue(oldPack.get(0).getData().equals(expResult.get(0).getData()));
        assertTrue(oldPack.get(0).getTarget().equals(expResult.get(0).getTarget()));
    }
    
}

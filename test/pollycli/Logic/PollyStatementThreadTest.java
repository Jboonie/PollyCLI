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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pollycli.DataStructures.PropertyPair;
import java.util.Properties;
import javafx.scene.control.ProgressBar;
import pollycli.DataStructures.FileStatusTracker;
import pollycli.DataStructures.PropertyPackage;

/**
 *
 * @author Jacob Boone #000752305
 */
public class PollyStatementThreadTest {
    
    private String testFileName;
    private String testTarget;
    private String testData;
    private File testFile;
    private PropertyPair testPair;
    private Properties property;
    private ArrayList<File> directoryContents;
    
    public PollyStatementThreadTest() throws FileNotFoundException, IOException {
        testFileName = "testFile.txt";
        testFile = new File(testFileName);
        testTarget = "A";
        testData = "B";
        testPair = new PropertyPair(testTarget, testData);
        property = new Properties();
        property.setProperty(testPair.getTarget(), testPair.getData());
        OutputStream out = new FileOutputStream(testFile);
        property.store(out, "");
        out.close();
        directoryContents = new ArrayList<>();
        directoryContents.add(testFile);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        File f = new File("testFile.txt");
        f.delete();
    }
    
    @Test
    public void testGetProperties(){
        PropertyManager pm = new PropertyManager(testFile.getPath());
        pm.readProperties();
        PropertyPackage pack = pm.getProperties();
        assertTrue(pack.get(0).getData().equals(testPair.getData()));
        assertTrue(pack.get(0).getTarget().equals(testPair.getTarget()));
    }
}

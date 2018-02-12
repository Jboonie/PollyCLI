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
package pollycli.Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollycli.DataStructures.PropertyPackage;
import pollycli.DataStructures.PropertyPair;

/**
 *
 * @author Jacob Boone
 */
public class PropertyWriteManager {

    private PropertyPackage propertyPackage;
    private File outputFile;

    public PropertyWriteManager(PropertyPackage propertyPackage, String filePath) {
        this.propertyPackage = propertyPackage;
        this.outputFile = new File(filePath);
        read();
        rewrite();
    }
    
    public void write(PropertyPackage propertyPackage, String filePath){
        this.propertyPackage = propertyPackage;
        this.outputFile = new File(filePath);
        rewrite();
    }
    
    private void read(){
        Properties properties = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(outputFile);
            properties.load(input);
            
            System.out.println(properties.toString());
            
            String data = properties.toString();
            data = data.replace("{", "");
            data = data.replace("}", "");
            
            ArrayList<String> splits = new ArrayList<String>(Arrays.asList(data.split(",")));
            
            for(int i = 0; i < splits.size(); i++){
                ArrayList<String> valuePairs = new ArrayList<String>(Arrays.asList(splits.get(i).split("=")));
                if(valuePairs.size() > 1){
                    propertyPackage.add(new PropertyPair(valuePairs.get(0), valuePairs.get(1)));
                }
                else{
                    propertyPackage.add(new PropertyPair(valuePairs.get(0), ""));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyWriteManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyWriteManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rewrite(){
        try {
            Properties properties = new Properties();
            for(int i = 0; i < propertyPackage.size(); i++){
                System.out.println(i + " Vs " + propertyPackage.size());
                properties.setProperty(propertyPackage.get(i).getTarget(), propertyPackage.get(i).getData());
            }   
            OutputStream output = new FileOutputStream(outputFile);
            properties.store(output, "");
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyWriteManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyWriteManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
}

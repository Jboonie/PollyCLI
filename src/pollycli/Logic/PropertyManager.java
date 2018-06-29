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
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pollycli.DataStructures.PropertyPackage;
import pollycli.DataStructures.PropertyPair;

/**
 *
 * @author Jacob Boone
 */
public class PropertyManager {
    private PropertyPackage properties;
    private File propertiesFile;

    public PropertyManager(String managedFile) {
        properties = new PropertyPackage();
        propertiesFile = new File(managedFile);
        readProperties();
    }

    public void readProperties() {
        try {
            Properties tempProperties = new Properties();
            InputStream inputStream = new FileInputStream(propertiesFile);
            tempProperties.load(inputStream);
            inputStream.close();
 
            Enumeration enumeration = tempProperties.keys();
            
            while(enumeration.hasMoreElements()){
                String key = (String) enumeration.nextElement();
                String value = tempProperties.getProperty(key);
                properties.add(new PropertyPair(key.trim(), value));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeProperties(){
        try{
           Properties tempProperties = new Properties();

            for(int i = 0; i < properties.size(); i++){
                tempProperties.setProperty(properties.get(i).getTarget(), properties.get(i).getData());
            }

            propertiesFile.delete();
            OutputStream outStream = new FileOutputStream(propertiesFile);
            tempProperties.store(outStream, null); 
            outStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addProperty(PropertyPair pair){
        if(!propertiesContain(pair)){
            properties.add(pair);
        }
    }
    
    private boolean propertiesContain(PropertyPair pair){
        if(properties.contains(pair)){
            properties.update(pair);
            return true;
        }
        return false;
    }
    
    public void setPackage(PropertyPackage newPack){
        properties = newPack;
    }
    
    public PropertyPackage getProperties(){
        readProperties();
        return properties;
    }
}

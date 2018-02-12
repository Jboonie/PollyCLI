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

/**
 *
 * @author Jacob Boone
 */
public class PropertyPackage {
    ArrayList<PropertyPair> properties;

    public PropertyPackage() {
        properties = new ArrayList<>();
    }

    public void add(PropertyPair pair){
        if(contains(pair.getTarget())){
            setByTarget(pair);
        }
        else{
            properties.add(pair);    
        }
    }
    
    public void remove(PropertyPair pair){
        if(properties.contains(pair)){
            properties.remove(pair);
        }
    }
    
    public ArrayList<PropertyPair> getPairs(){
        return properties;
    }
    
    public PropertyPair get(int i){
        return properties.get(i);
    }
    
    public int size(){
        return properties.size();
    }
    
    public void set(int i, PropertyPair pair){
        properties.set(i, pair);
    }
    
    public boolean hasSetting(String str){
        for(PropertyPair pair : properties){
            if(pair.getTarget().equals(str)){
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(String str){
        for(PropertyPair pair : properties){
            if(pair.getData().equals(str) || pair.getTarget().equals(str)){
                return true;
            }
        }
        return false;
    }
    
    public PropertyPair getByTarget(String str){
        if(contains(str)){
            for(PropertyPair pair : properties){
                if(pair.getTarget().equals(str)){
                    return pair;
                }
            }
        }
        return null;
    }
    
    public PropertyPair getByData(String str){
        if(contains(str)){
                for(PropertyPair pair : properties){
                    if(pair.getData().equals(str)){
                        return pair;
                    }
                }
            }
            return null;
        }
    
    public void setByTarget(PropertyPair pair){
        if(contains(pair.getTarget())){
            for(PropertyPair p : properties){
                if(p.getTarget().equals(pair.getTarget())){
                    p.setData(pair.getData());
                }
            }
        }
    }
}

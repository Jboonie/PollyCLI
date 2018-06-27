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

import java.io.File;
import pollycli.StaticData.Strings;

/**
 *
 * @author Jacob Boone
 */
public class PollyStatement {
    private PropertyPackage pack;
    
    //Property info that is important to form a polly statement.
    private String[] trackedStatements = Strings.TRACKED_STATEMENT_PROPERTY_TARGETS;
    
    private boolean isWorking = false;

    public PollyStatement() {
        this.pack = new PropertyPackage();
    }
    
    public String getStatement(String text, File outputFile){
        String returnValue = "";
        returnValue += Strings.SETTINGS_STATEMENT_INITIAL_CONTENTS;
        returnValue += Strings.SETTINGS_STATEMENT_PART01_CONTENTS;
        
        PropertyPair aws = pack.getByTarget(Strings.SETTINGS_AWSCMD);        
        returnValue += aws.getData();
        
        returnValue += Strings.SETTINGS_STATEMENT_PART02_CONTENTS;
        
        PropertyPair output = pack.getByTarget(Strings.SETTINGS_OUTPUT);        
        returnValue += output.getData();
        
        returnValue += Strings.SETTINGS_STATEMENT_PART03_CONTENTS;
        
        text = text.replace("\"", "");
        text = text.replace("\n", " ");
        returnValue += text;
        
        returnValue += Strings.SETTINGS_STATEMENT_PART04_CONTENTS;
        
        PropertyPair voice = pack.getByTarget(Strings.SETTINGS_SPEAKER);        
        returnValue += voice.getData();
        
        returnValue += Strings.SETTINGS_STATEMENT_PART05_CONTENTS;
        
        returnValue += convertExtension(outputFile, output.getData());
        
        returnValue += Strings.SETTINGS_STATEMENT_PART06_CONTENTS;

        return returnValue;
    }
    
    private String convertExtension(File file, String newExt){
        String returnVal = file.getAbsoluteFile().toString();
        returnVal = returnVal.replace(".txt", "." + newExt);
        return returnVal;
    }
    
    public void loadPack(PropertyPackage newPack){
        this.pack = new PropertyPackage();
        if(isVerified(trackedStatements, newPack)){
            buildPack(newPack);
        }
    }
    
    public boolean packLoadedSuccessfully(PropertyPackage pack){
        return isVerified(trackedStatements, pack);
    }
    
    //Populates internal package with appropriate data from the Strings class
    private void buildPack(PropertyPackage pack){
        for(int i = 0; i < trackedStatements.length; i++){
            for(int j = 0; j < pack.size(); j++){
                if(pack.get(j).getTarget().equals(trackedStatements[i])){
                    this.pack.add(new PropertyPair(pack.get(j).getTarget(), pack.get(j).getData()));
                }
            }
        }
    }

    private boolean isVerified(String[] trackedStrings, PropertyPackage incomingPack){
        for(int i = 0; i < trackedStrings.length; i++){
            if(!incomingPack.contains(trackedStrings[i])){
                return false;
            }
        }
        isWorking = true;
        return true;
    }
    
    public boolean isValid(){
        return isWorking;
    }
}

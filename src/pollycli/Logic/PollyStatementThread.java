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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import pollycli.DataStructures.FileStatusTracker;
import pollycli.DataStructures.PollyStatement;
import pollycli.DataStructures.PropertyPackage;
import pollycli.StaticData.Paths;

/**
 *
 * @author Jacob Boone
 */
public class PollyStatementThread extends Thread{

    private ArrayList<File> directoryContents;
    private PropertyPackage propertyPackage;
    private ArrayList<FileStatusTracker> targetFiles;
    private ProgressBar progressBar;
    
    public PollyStatementThread(ArrayList<File> contents, ArrayList<FileStatusTracker> fileDisplay, ProgressBar progressBar) {
        targetFiles = fileDisplay;
        directoryContents = contents;
        propertyPackage = getProperties();
        this.progressBar = progressBar;
        start();
    }
    
    public void run(){
        progressBar.setVisible(true);
        progressBar.setProgress(0);
        PollyStatement statement = new PollyStatement();
        if(statement.loadPack(propertyPackage)){
            if(directoryContents.size() > 0){
                for(int i = 0; i < directoryContents.size(); i++){
                    try{
                        String textPortion = readFile(directoryContents.get(i).toString());
                        String runString = statement.getStatement(textPortion, directoryContents.get(i));
                        Runtime.getRuntime().exec(runString);   
                        updateUIRepresentation(directoryContents.get(i));
                        System.out.println(runString);
                        progressBar.setProgress((double) i / (double) directoryContents.size());
                        if(i != 0){
                            if((i % 60) == 0){
                            System.out.println("Sleeping: " + i + " % 60 is " + (i % 60));
                            Thread.sleep(2000);
                            }
                        }
                    }
                    catch(Exception e){
                        System.out.println("Broke!");
                        e.printStackTrace();
                    }
                }
            }
        }
        progressBar.setVisible(false);
    }
    
    String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
    private PropertyPackage getProperties(){
        PropertyManager propManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        propManager.readProperties();
        PropertyPackage returnPackage = propManager.getProperties();
        return returnPackage;
    }

    private void updateUIRepresentation(File get) {
        for(int i = 0; i < targetFiles.size(); i++)
        {
            if(targetFiles.get(i).getFile().equals(get)){
                targetFiles.get(i).getFileDisplayItem().toggleStatus();
            }
        }
    }
    
}

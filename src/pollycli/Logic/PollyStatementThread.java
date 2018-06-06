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
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.scene.control.ProgressBar;
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
    private static final int processLimit = 60;
    public static final int threadSleepTime = 2000;
    
    public PollyStatementThread(ArrayList<File> contents, ArrayList<FileStatusTracker> fileDisplay, ProgressBar progressBar) {
        targetFiles = fileDisplay;
        directoryContents = contents;
        propertyPackage = getProperties();
        this.progressBar = progressBar;
        start();
    }
    
    private PropertyPackage getProperties(){
        PropertyManager propManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        propManager.readProperties();
        PropertyPackage returnPackage = propManager.getProperties();
        return returnPackage;
    }
    
    public void run(){
        progressBarVisible(true);
        buildStatement();
        progressBarVisible(false);
    }
    
    private void progressBarVisible(boolean visible) {
        progressBar.setVisible(visible);
        progressBar.setProgress(0);
    }
    
    private void buildStatement(){
        PollyStatement statement = new PollyStatement();
        if(canExecuteStatement(statement)){
            statement.loadPack(propertyPackage);
            IntStream stream = IntStream.range(0, directoryContents.size());
            stream.forEach(i -> executeStatement(i, statement));
        }
    }
    
    private boolean canExecuteStatement(PollyStatement statement){
        if(directoryContents.size() > 0){
            if(statement.packLoadedSuccessfully(propertyPackage)){
                return true;
            }
        }
        return false;
    }
    
    private void executeStatement(int i, PollyStatement statement){
        try {
                Runtime.getRuntime().exec(buildExecutableString(statement, directoryContents.get(i)));
                updateUI(directoryContents.get(i));
                progressBar.setProgress((double) i / (double) directoryContents.size());
                pauseThread(i);
            } catch (Exception ex) {
                Logger.getLogger(PollyStatementThread.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private String buildExecutableString(PollyStatement statement, File file){
        try{
            String text = readFile(file.toString());
            return statement.getStatement(text, file);
        } catch(Exception ex){
            Logger.getLogger(PollyStatementThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private String readFile(String fileName) throws IOException {
        try {
            StringBuilder returnString = new StringBuilder();
            Stream<String> stream = Files.lines(java.nio.file.Paths.get(fileName));
            stream.forEach(str -> returnString.append(str));
            return returnString.toString();
        } catch(Exception ex){
            Logger.getLogger(PollyStatementThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private void updateUI(File file) {
        IntStream range = IntStream.range(0, targetFiles.size());
        range.filter(i -> targetFiles.get(i).getFile().equals(file))
             .forEach(i -> targetFiles.get(i).getFileDisplayItem().toggleStatus());
    }
    
    private void pauseThread(int i){
        try{
            if((i > 0) && (i % processLimit == 0)){
                Thread.sleep(threadSleepTime);
            }
        }catch(Exception ex){
            Logger.getLogger(PollyStatementThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

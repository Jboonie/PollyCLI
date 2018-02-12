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
package pollycli.Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.NativeArray;
import pollycli.DataStructures.FileDisplayItem;
import pollycli.DataStructures.FileStatusTracker;
import pollycli.DataStructures.PollyStatement;
import pollycli.DataStructures.PropertyPackage;
import pollycli.Logic.PollyStatementThread;
import pollycli.Logic.PropertyManager;
import pollycli.StaticData.Paths;
import pollycli.StaticData.Strings;

/**
 *
 * @author Jacob Boone
 */
public class MainPageController implements Initializable {
    
    //NEW IMP
    private String SETTINGS_OUTPUT;
    private String SETTINGS_AWSCMD;
    private String SETTINGS_NUMREQS;
    private String SETTINGS_SPEAKER;
    //END NEW IMP
    
    
    private File trackedDirectory;
    private ArrayList<File> directoryContents;
    private String awsCMDDir = "\"C:\\Users\\Stacker\\AppData\\Roaming\\Python\\Python36\\Scripts\\aws.cmd";
    private String pollyInitialStatement = " polly synthesize-speech --output-format=\"mp3\" --text=\"";
    private String pollySecondaryStatement = "\" --voice-id=\"Salli\" ";
    private String narrator = "Salli";
    private String outputFormat = "mp3";
    private String formedStatement;
    private Charset utf = Charset.defaultCharset();
    private PropertyPackage propertyPackage;
    
    private ArrayList<FileStatusTracker> targetFiles;
    
    @FXML
    private TextField textField;
    
    @FXML 
    private TextArea textArea;
    
    @FXML
    private ScrollPane testScrollPane;
    @FXML
    private VBox testVBox;
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private void loadDirectory(ActionEvent e){
        directoryContents = new ArrayList<File>();
        //textArea.setText("");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(trackedDirectory);
        trackedDirectory = directoryChooser.showDialog(null);
        textField.setText(trackedDirectory.getPath());
        
        //TESTING
//        test(trackedDirectory);

        File[] tempFileList = trackedDirectory.listFiles();
        
        for(int i = 0; i < tempFileList.length; i++){
            if(tempFileList[i].getName().contains(".txt")){
                //textArea.setText(textArea.getText() + tempFileList[i].getName() + "\n");
                directoryContents.add(tempFileList[i]);
            }
        }
        
        
        //NEW IMPLEMENTATION
        for(File file : tempFileList){
            if(file.getName().contains(".txt")){
                FileDisplayItem newDisplay = new FileDisplayItem(file.getName());
                testVBox.getChildren().add(newDisplay);
                targetFiles.add(new FileStatusTracker(newDisplay, file));
            }
        }
    }
    
    private boolean getStoredSettings(){
        PropertyManager propertyManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        PropertyPackage pack = propertyManager.getProperties();
        
        System.out.println("PACK SIZE: " + pack.size());
        
        for(int i = 0; i < pack.size(); i++){
            System.out.println(" OUTPUT: " + pack.get(i).getTarget() + " " + pack.get(i).getData());
        }
        
        if(pack.hasSetting(Strings.SETTINGS_AWSCMD)){
            SETTINGS_AWSCMD = pack.getByTarget(Strings.SETTINGS_AWSCMD).getData();
        }
        
        if(pack.hasSetting(Strings.SETTINGS_NUMREQS)){
            SETTINGS_NUMREQS = pack.getByTarget(Strings.SETTINGS_NUMREQS).getData();
        }
        
        if(pack.hasSetting(Strings.SETTINGS_OUTPUT)){
            SETTINGS_OUTPUT = pack.getByTarget(Strings.SETTINGS_OUTPUT).getData();
        }
        
        if(pack.hasSetting(Strings.SETTINGS_SPEAKER)){
            SETTINGS_SPEAKER = pack.getByTarget(Strings.SETTINGS_SPEAKER).getData();
        }
        
        if((SETTINGS_AWSCMD != null) && (SETTINGS_NUMREQS != null) && (SETTINGS_OUTPUT != null) && (SETTINGS_SPEAKER != null)){
            System.out.println("SUCCESS (getStoredSettings())!");
            return true;
        }
        else{
            System.out.println("ERROR (getStoredSettings()): " + SETTINGS_AWSCMD + " " + SETTINGS_NUMREQS + " " + SETTINGS_OUTPUT + " " + SETTINGS_SPEAKER);
            return false;   
        }
    }
        @FXML
    private void convertFiles(ActionEvent event) throws IOException{
        PollyStatementThread newThread = new PollyStatementThread(directoryContents, targetFiles, progressBar);
    }
    /*
    @FXML
    private void convertFiles(ActionEvent event) throws IOException{
        System.out.println(getStoredSettings());
        if(loadProperties()){
            System.out.println("Properties Loaded!");        
            if(directoryContents.size() > 0){
                for(int i = 0; i < directoryContents.size(); i++){
                    formedStatement = buildStatement(trackedDirectory, directoryContents.get(i), i);
                    try{
                        System.out.println("doing somthing pt 2");
                        Runtime.getRuntime().exec("cmd /C start cmd.exe /K" + formedStatement);
                        System.out.println("cmd /C start cmd.exe /K" + formedStatement);
                        if(i != 0){
                            if((i % 60) == 0){
                            System.out.println("Sleeping: " + i + " % 60 is " + (i % 60));
                            Thread.sleep(30000);
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
        else{
            System.out.println("Missing some critical configurations! Please review the config utility");
            launchSettings(event);
        } 
    }
    */
    
    @FXML
    private void launchSettings(ActionEvent event){
        try {
            
            ResourceBundle resources = ResourceBundle.getBundle(Paths.LANG_EN);
            String path = Paths.SETTINGSFXML;
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource(path), resources);
            Parent root = addPartLoader.load();
            Stage newStage = new Stage(); 
            
            Scene scene = new Scene(root);
            
            newStage.setScene(scene);
            
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML 
    private void launchAbout(ActionEvent event){
        try {
            String path = Paths.ABOUTFXML;
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = addPartLoader.load();
            Stage newStage = new Stage(); 
            
            Scene scene = new Scene(root);
            
            newStage.setScene(scene);
            
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trackedDirectory = new File("C:\\");
        directoryContents = new ArrayList<>();
        targetFiles = new ArrayList<>();
        textArea.setEditable(false);
        textArea.setVisible(false);
        formedStatement = "";
        
        testVBox.getStylesheets().clear();
        testVBox.getStylesheets().add(Strings.CSS_FILE_PATH);
        testVBox.getStyleClass().clear();
        testVBox.getStyleClass().add(Strings.FILE_DISPLAY_VBOX_CSS);
        
        propertyPackage = getProperties();
        for(int i = 0; i < propertyPackage.size(); i++){
            System.out.println("LOAD PROP:" + propertyPackage.get(i).getTarget() + " " + propertyPackage.get(i).getData());
        }
    }    

    private PropertyPackage getProperties(){
        PropertyManager propManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        propManager.readProperties();
        PropertyPackage returnPackage = propManager.getProperties();
        return returnPackage;
    }
    
//    private String buildStatement(File trackedDirectory, File get, int x) throws IOException {
//        String returnValue = "";
//        PollyStatement ps = new PollyStatement();
//        
//        if(get.isFile()){
//           
//            String textPortion = readFile(get.toPath().toString());
//            textPortion = textPortion.replace("\"", "");
//            textPortion = textPortion.replace("\n", " ");
//            textPortion = textPortion.trim();
//            returnValue = awsCMDDir + pollyInitialStatement + textPortion + pollySecondaryStatement 
//                    + "\"" + trackedDirectory.getPath() + File.separator + removeExt(get.getName()) + ".mp3\"\"";
//            System.out.println(returnValue);
//            if(ps.loadPack(propertyPackage)){
//                System.out.println("VS");
//                System.out.println(ps.getStatement(textPortion, get));    
//            }
//            else{
//                System.out.println("ProperyPack couldnt load in the property PollyStatement PS");
//            }
//        }
//        return returnValue;
//    }
    
    private String removeExt(String str){
        return str.replace(".txt", ".mp3");
    }
    
//    String readFile(String fileName) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//        try {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                sb.append("\n");
//                line = br.readLine();
//            }
//            return sb.toString();
//        } finally {
//            br.close();
//        }
//    }
}

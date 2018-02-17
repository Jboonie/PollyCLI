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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pollycli.DataStructures.FileDisplayItem;
import pollycli.DataStructures.FileStatusTracker;
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
    
    //CLASS VARIABLES
    private File trackedDirectory;
    private ArrayList<File> directoryContents;
    private PropertyPackage propertyPackage;
    private ArrayList<FileStatusTracker> targetFiles;
    
    @FXML
    private TextField textField;
    @FXML
    private VBox outputDisplayVBox;
    @FXML
    private ProgressBar progressBar;
    
    //ATTACHED TO BROWSE BUTTON
    @FXML
    private void loadDirectory(ActionEvent e){
        directoryContents = new ArrayList<File>();
        targetFiles = new ArrayList<>();
        outputDisplayVBox.getChildren().clear();
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(trackedDirectory);
        trackedDirectory = directoryChooser.showDialog(null);
        textField.setText(trackedDirectory.getPath());

        File[] tempFileList = trackedDirectory.listFiles();
        
        for(File file : tempFileList){
            if(file.isFile()){
                if(file.getName().contains(Strings.FILE_EXTENSION_SEPERATOR)){
                    String fileExt = file.getName().substring(file.getName().indexOf(Strings.FILE_EXTENSION_SEPERATOR));
                    if(Strings.SUPPORTED_INPUT.contains(fileExt)){
                        directoryContents.add(file);
                        FileDisplayItem newDisplay = new FileDisplayItem(file.getName());
                        outputDisplayVBox.getChildren().add(newDisplay);
                        targetFiles.add(new FileStatusTracker(newDisplay, file));
                    }
                }
            }
        }
    }
    
    //ATTACHED TO CONVERT BUTTON
    @FXML
    private void convertFiles(ActionEvent event) throws IOException{
        new PollyStatementThread(directoryContents, targetFiles, progressBar);
    }
  
    //ATTACHED TO CONFIGURE BUTTON
    @FXML
    private void launchSettings(ActionEvent event){
        try {
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource(Paths.SETTINGSFXML), Paths.ENG_BUNDLE);
            Parent root = addPartLoader.load();
            Stage newStage = new Stage(); 
            
            Scene scene = new Scene(root);
            
            newStage.setScene(scene);
            newStage.getIcons().add(Paths.IMAGE_BIRD);
            newStage.setTitle(Strings.SettingsPageTitle);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //ATTACHED TO ABOUT BUTTON
    @FXML 
    private void launchAbout(ActionEvent event){
        try {
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource(Paths.ABOUTFXML), Paths.ENG_BUNDLE);
            Parent root = addPartLoader.load();
            Stage newStage = new Stage(); 
            
            Scene scene = new Scene(root);
            
            newStage.setScene(scene);
            newStage.getIcons().add(Paths.IMAGE_BIRD);
            newStage.setTitle(Strings.AboutPageTitle);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trackedDirectory = new File(Strings.FILE_DEFAULT_BROWSE_DIRECTORY);
        directoryContents = new ArrayList<>();
        targetFiles = new ArrayList<>();
        
        textField.setEditable(false);
        
        outputDisplayVBox.getStylesheets().clear();
        outputDisplayVBox.getStylesheets().add(Strings.CSS_FILE_PATH);
        outputDisplayVBox.getStyleClass().clear();
        outputDisplayVBox.getStyleClass().add(Strings.FILE_DISPLAY_VBOX_CSS);
        
        propertyPackage = getProperties();
    }    

    private PropertyPackage getProperties(){
        PropertyManager propManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        propManager.readProperties();
        PropertyPackage returnPackage = propManager.getProperties();
        return returnPackage;
    }
}

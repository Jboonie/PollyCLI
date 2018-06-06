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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import pollycli.DataStructures.FileDisplayItem;
import pollycli.DataStructures.FileStatusTracker;
import pollycli.DataStructures.PropertyPackage;
import pollycli.DataStructures.SupportedLanguage;
import pollycli.DataStructures.UIController;
import pollycli.Logic.PollyStatementThread;
import pollycli.Logic.PropertyManager;
import pollycli.StaticData.Paths;
import pollycli.StaticData.Strings;

/**
 *
 * @author Jacob Boone
 */
public class MainPageController extends UIController implements Initializable {
    
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
    @FXML
    private Menu mainLangMenu;
    @FXML
    private AnchorPane mainPane;
    
    //ATTACHED TO BROWSE BUTTON
    @FXML
    private void loadDirectory(ActionEvent e){
        directoryContents = new ArrayList<File>();
        targetFiles = new ArrayList<>();
        outputDisplayVBox.getChildren().clear();
        
        textField.setText(chooseTargetDirectory()); 
        findValidTargetFiles(trackedDirectory.listFiles());
    }
    
    private void findValidTargetFiles(File[] initialFileList){
        ArrayList<File> fileList = new ArrayList<>(Arrays.asList(initialFileList));
        fileList.stream()
                .filter(f -> f.getName().contains(Strings.FILE_EXTENSION_SEPERATOR))                
                .filter(f -> Strings.SUPPORTED_INPUT.contains(getFileExt(f)))
                .forEach(f -> {
                    directoryContents.add(f);
                    FileDisplayItem newDisplay = new FileDisplayItem(f.getName());
                    outputDisplayVBox.getChildren().add(newDisplay);
                    targetFiles.add(new FileStatusTracker(newDisplay, f));
                });
    }
    
    private String getFileExt(File file) {
        return file.getName().substring(file.getName().indexOf(Strings.FILE_EXTENSION_SEPERATOR));
    }
    
    
    
    private String chooseTargetDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(trackedDirectory);
        trackedDirectory = directoryChooser.showDialog(null);
        return trackedDirectory.getPath();
    }
    
    @FXML
    private void convertFiles(ActionEvent event) throws IOException{
        new PollyStatementThread(directoryContents, targetFiles, progressBar);
    }
  
    @FXML
    private void launchSettings(ActionEvent event){
        showStage(Paths.SETTINGSFXML, Strings.SettingsPageTitle);
    }
    
    @FXML 
    private void launchAbout(ActionEvent event){
        showStage(Paths.ABOUTFXML, Strings.AboutPageTitle);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        propertyPackage = getProperties();
        initializeVariables();
        initializeFormatting();
        initializeSupportedLanguages();
    }
    
    private PropertyPackage getProperties(){
        PropertyManager propManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        propManager.readProperties();
        PropertyPackage returnPackage = propManager.getProperties();
        return returnPackage;
    }

    private void initializeVariables(){
        trackedDirectory = new File(Strings.FILE_DEFAULT_BROWSE_DIRECTORY);
        directoryContents = new ArrayList<>();
        targetFiles = new ArrayList<>();
    }
    
    public void initializeFormatting(){
        textField.setEditable(false);
        outputDisplayVBox.getStylesheets().clear();
        outputDisplayVBox.getStylesheets().add(Strings.CSS_FILE_PATH);
        outputDisplayVBox.getStyleClass().clear();
        outputDisplayVBox.getStyleClass().add(Strings.FILE_DISPLAY_VBOX_CSS);
    }

    private void initializeSupportedLanguages() {
        for(SupportedLanguage lang : Paths.SUPPORTED_LANGUAGES){
            MenuItem newItem = new MenuItem(lang.getNAME());
            
            newItem.setOnAction((event) -> {
                activeLanguage = lang.getBUNDLE();
                System.out.println("NEW AL: " + activeLanguage.toString() + " AKA " + lang.getNAME());
                try {
                    FXMLLoader newLanguageLoader = new FXMLLoader(getClass().getResource(Paths.MAINFXML), lang.getBUNDLE());
                    
                    Parent root = newLanguageLoader.load();
                    
                    MainPageController newMainController = newLanguageLoader.<MainPageController>getController();
                    //PASS NEW LANGUAGE TO NEW VERSION OF MAIN MENU CONTROLLER PRIOR TO SHOW
                    newMainController.setLanguage(activeLanguage);
                    
                    Scene scene = mainPane.getScene();
                    scene.setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            mainLangMenu.getItems().add(newItem);
        }
    }
    
    public void setLanguage(ResourceBundle newBundle){
        activeLanguage = newBundle;
    }
}

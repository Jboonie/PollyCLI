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
import java.nio.charset.Charset;
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
import javafx.scene.control.TextArea;
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
    
    private File trackedDirectory;
    private ArrayList<File> directoryContents;
    private Charset utf = Charset.defaultCharset();
    private PropertyPackage propertyPackage;
    
    private ArrayList<FileStatusTracker> targetFiles;
    
    @FXML
    private TextField textField;
    
    @FXML 
    private TextArea textArea;

    @FXML
    private VBox testVBox;
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private void loadDirectory(ActionEvent e){
        directoryContents = new ArrayList<File>();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(trackedDirectory);
        trackedDirectory = directoryChooser.showDialog(null);
        textField.setText(trackedDirectory.getPath());

        File[] tempFileList = trackedDirectory.listFiles();
        
        for(int i = 0; i < tempFileList.length; i++){
            if(tempFileList[i].getName().contains(".txt")){
                directoryContents.add(tempFileList[i]);
            }
        }

        for(File file : tempFileList){
            if(file.getName().contains(".txt")){
                FileDisplayItem newDisplay = new FileDisplayItem(file.getName());
                testVBox.getChildren().add(newDisplay);
                targetFiles.add(new FileStatusTracker(newDisplay, file));
            }
        }
    }
    
        @FXML
    private void convertFiles(ActionEvent event) throws IOException{
        PollyStatementThread newThread = new PollyStatementThread(directoryContents, targetFiles, progressBar);
    }
  
    @FXML
    private void launchSettings(ActionEvent event){
        try {
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource(Paths.SETTINGSFXML), Paths.ENG_BUNDLE);
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
        
        testVBox.getStylesheets().clear();
        testVBox.getStylesheets().add(Strings.CSS_FILE_PATH);
        testVBox.getStyleClass().clear();
        testVBox.getStyleClass().add(Strings.FILE_DISPLAY_VBOX_CSS);
        
        propertyPackage = getProperties();
    }    

    private PropertyPackage getProperties(){
        PropertyManager propManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        propManager.readProperties();
        PropertyPackage returnPackage = propManager.getProperties();
        return returnPackage;
    }
}

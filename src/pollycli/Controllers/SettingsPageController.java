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
 * FITNESS FOR settingsGeneralPane PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pollycli.Controllers;

import pollycli.DataStructures.SettingsCombo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pollycli.DataStructures.PropertyPackage;
import pollycli.DataStructures.PropertyPair;
import pollycli.Logic.PropertyManager;
import pollycli.StaticData.Paths;
import pollycli.StaticData.Strings;

/**
 *
 * @author Jacob Boone
 */
public class SettingsPageController implements Initializable{
    
    // Scroll Panes
    @FXML
    private ScrollPane settingsGeneralPane;
    @FXML
    private ScrollPane settingsAWSPane;
    @FXML
    private ScrollPane settingsNarrationPane;
    @FXML
    private ScrollPane settingsOutputPane;
    
    // Navigation Buttons
    @FXML
    private Button GeneralPaneButton;
    @FXML
    private Button AWSPaneButton;
    @FXML
    private Button NarrationPaneButton;
    @FXML
    private Button OutputPaneButton;
    
    // Save Buttons
    @FXML
    private Button AWSSaveButton;
    @FXML
    private Button NarrationSaveButton;  
    @FXML
    private Button OutputSaveButton;
    
    
    // ChoiceBoxes
    @FXML
    private ChoiceBox FileTypeChoiceBox; 
    @FXML
    private ChoiceBox NarrationChoiceBox;   
    // Text Fields
    @FXML
    private TextField AWSTextField;   
    //Label
    @FXML
    private Label AWSAWSLabel;
    
    // Class Variables
    private ArrayList<SettingsCombo> settingsCombo;
    private Properties properties;
    private File propertiesFile;
    private File TempAWSFile;
    private File AWSFile;
    private PropertyManager propertyManager;
    
    @FXML
    private void settingsSelector(ActionEvent event){
        Button button = (Button) event.getSource();
        showPane(button);
    }

    private void showPane(Button button){
        IntStream streamOfCombos = IntStream.range(0, settingsCombo.size());
        streamOfCombos.forEach(i -> {
            if(buttonMatches(settingsCombo.get(i), button)){
                setPaneVisibilityOfCombo(settingsCombo.get(i), true);
            }else{
                setPaneVisibilityOfCombo(settingsCombo.get(i), false);
            }
        });
    }
    
    private boolean buttonMatches(SettingsCombo combo, Button button){
        if(combo.getButton().getId().equals(button.getId())){
            return true;
        }
        return false;
    }
    
    private void setPaneVisibilityOfCombo(SettingsCombo combo, boolean visible) {
        combo.getPane().setVisible(visible);
    }
    
    @FXML 
    private void browseFile(ActionEvent event){
        FileChooser fileChoice = new FileChooser();
        TempAWSFile = fileChoice.showOpenDialog(null);
    }
    
    @FXML
    private void saveData(ActionEvent event){
        propertyManager.readProperties();
        Button button = (Button) event.getSource();
        
        PropertyPackage pack = new PropertyPackage();
        
        //AWS
//        saveAWS();
        if(button.getId().equals(AWSSaveButton.getId())){
            if(AWSTextField.getText() != null){
                String data = AWSTextField.getText();
                if(data.matches("[0-9]*")){
                    pack.add(new PropertyPair(Strings.SETTINGS_NUMREQS, data));
                }
            }
            if(TempAWSFile != null){
                pack.add(new PropertyPair(Strings.SETTINGS_AWSCMD, TempAWSFile.getAbsolutePath()));
                AWSFile = TempAWSFile;
            }
        }
        
        //Narration
//        saveNarration();
        if(button.getId().equals(NarrationSaveButton.getId())){
            String data = (String) NarrationChoiceBox.getValue();
            if(data != null){
                pack.add(new PropertyPair(Strings.SETTINGS_SPEAKER, data));
            }
        }
        
        //Output
//        saveOutput();
        if(button.getId().equals(OutputSaveButton.getId())){
            String data = (String) FileTypeChoiceBox.getValue();
            System.out.println(data);
            if(data != null){
                pack.add(new PropertyPair(Strings.SETTINGS_OUTPUT, data));
            }
        }
        if(pack.size() > 0){
            IntStream stream = IntStream.range(0, pack.size());
            stream.forEach(i -> propertyManager.addProperty(pack.get(i)));
            propertyManager.writeProperties();
        }
    }
    
    @FXML
    public void awsHelp(ActionEvent event){
        try {
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource(Paths.AWSHELPFXML), Paths.ENG_BUNDLE);
            Parent root = addPartLoader.load();
            Stage newStage = new Stage(); 
            
            Scene scene = new Scene(root);
            
            newStage.setScene(scene);
            newStage.getIcons().add(Paths.IMAGE_BIRD);
            newStage.setTitle(Strings.SettingsAWSHelpTitle);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settingsCombo = new ArrayList<>();
        settingsCombo.add(new SettingsCombo(GeneralPaneButton, settingsGeneralPane));
        settingsCombo.add(new SettingsCombo(AWSPaneButton, settingsAWSPane));
        settingsCombo.add(new SettingsCombo(NarrationPaneButton, settingsNarrationPane));
        settingsCombo.add(new SettingsCombo(OutputPaneButton, settingsOutputPane));
        
        properties = new Properties();
        propertiesFile = new File(Paths.CLIENT_PROPERTIES);
        
        TempAWSFile = null;
        
        loadChoiceBoxes();
        propertyManager = new PropertyManager(Paths.CLIENT_PROPERTIES);
        populateFieldsWithCurrentSettings(propertyManager);
    }
    
    private void loadChoiceBoxes(){     
        for(String s : Strings.NARRATORS){
            NarrationChoiceBox.getItems().add(s);
        }
        for(String s : Strings.FILE_TYPES){
            FileTypeChoiceBox.getItems().add(s);
        }
    }

    private void populateFieldsWithCurrentSettings(PropertyManager propertyManager) {
        propertyManager.readProperties();
        PropertyPackage pack = propertyManager.getProperties();
        
        IntStream stream = IntStream.range(0, pack.size());
        stream.forEach(i -> {
            propertyTargetsAwscmd(pack.get(i));
            propertyTargetsNumreqs(pack.get(i));
            propertyTargetsOutput(pack.get(i));
            propertyTargetsSpeaker(pack.get(i));
        });
    }
    
    private void propertyTargetsAwscmd(PropertyPair pack) {
          //NOT IMPLEMENTED YET
//        if(pack.getTarget().equals(Strings.SETTINGS_AWSCMD)){
//                //AWSAWSLabel.setText(pack.getData());
//            }
    }

    private void propertyTargetsNumreqs(PropertyPair pack) {
        if(pack.getTarget().equals(Strings.SETTINGS_NUMREQS)){
                AWSTextField.setText(pack.getData());
            }
    }

    private void propertyTargetsOutput(PropertyPair pack) {
        if(pack.getTarget().equals(Strings.SETTINGS_OUTPUT)){
                FileTypeChoiceBox.setValue(pack.getData());
            }
    }

    private void propertyTargetsSpeaker(PropertyPair pack) {
        if(pack.getTarget().equals(Strings.SETTINGS_SPEAKER)){
                NarrationChoiceBox.setValue(pack.getData());
            }
    }
}

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pollycli.DataStructures.PropertyPackage;
import pollycli.DataStructures.PropertyPair;
import pollycli.Logic.PropertyManager;
import pollycli.StaticData.Paths;
import pollycli.StaticData.Strings;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    @FXML
    private void saveSettings(ActionEvent event){
        OutputStream out = null;
        try {
            /*
            awsCMDDir = properties.getProperty("AWSDirectory");
            pollyInitialStatement = properties.getProperty("AWSPrimaryStatement");
            pollySecondaryStatement = properties.getProperty("AWSSecondaryStatement");
            
            private String awsCMDDir = "\"C:\\Users\\Stacker\\AppData\\Roaming\\Python\\Python36\\Scripts\\aws.cmd";
            private String pollyInitialStatement = " polly synthesize-speech --output-format=\"mp3\" --text=\"";
            private String pollySecondaryStatement = "\" --voice-id=\"Salli\" ";
            */  
            properties.setProperty("AWSDirectory", "\"C:\\Users\\Stacker\\AppData\\Roaming\\Python\\Python36\\Scripts\\aws.cmd");
            properties.setProperty("AWSPrimaryStatement", " polly synthesize-speech --output-format=\"mp3\" --text=\"");
            properties.setProperty("AWSSecondaryStatement", "\" --voice-id=\"Mizuki\" ");
            out = new FileOutputStream(propertiesFile);
            try {
                properties.store(out, "");
            } catch (IOException ex) {
                Logger.getLogger(SettingsPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingsPageController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SettingsPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void showPane(Button b){
        for(int i = 0; i < settingsCombo.size(); i++){
            if(settingsCombo.get(i).getButton().getId().toString().equals(b.getId().toString())){
                settingsCombo.get(i).getPane().setVisible(true);
            }
            else{
                settingsCombo.get(i).getPane().setVisible(false);
            }
        }
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
        if(button.getId().equals(NarrationSaveButton.getId())){
            String data = (String) NarrationChoiceBox.getValue();
            if(data != null){
                pack.add(new PropertyPair(Strings.SETTINGS_SPEAKER, data));
            }
        }
        
        //Output
        if(button.getId().equals(OutputSaveButton.getId())){
            String data = (String) FileTypeChoiceBox.getValue();
            System.out.println(data);
            if(data != null){
                pack.add(new PropertyPair(Strings.SETTINGS_OUTPUT, data));
            }
        }
        if(pack.size() > 0){
                for(int i = 0; i < pack.size(); i++){
                    propertyManager.addProperty(pack.get(i));
                    System.out.println("ADDING: " + pack.get(i).getData());
                }
                propertyManager.writeProperties();
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
    }
    
    private void loadChoiceBoxes(){            
        NarrationChoiceBox.getItems().add("Nicole");
        NarrationChoiceBox.getItems().add("Russell");
        NarrationChoiceBox.getItems().add("Amy");
        NarrationChoiceBox.getItems().add("Brian");
        NarrationChoiceBox.getItems().add("Emma");
        NarrationChoiceBox.getItems().add("Joanna");
        NarrationChoiceBox.getItems().add("Matthew");
        NarrationChoiceBox.getItems().add("Salli");
        NarrationChoiceBox.getItems().add("Justin");
        NarrationChoiceBox.getItems().add("Kendra");
        NarrationChoiceBox.getItems().add("Joey");
        NarrationChoiceBox.getItems().add("Kimberly");
        NarrationChoiceBox.getItems().add("Ivy");
        
        FileTypeChoiceBox.getItems().add("mp3");
    }
}

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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import pollycli.StaticData.Paths;
import pollycli.StaticData.Strings;

/**
 * FXML Controller class
 *
 * @author Jacob Boone
 */
public class AWSHelpPageController implements Initializable {

//    @FXML
//    private ScrollPane testingScrollPane;
//    @FXML
//    private Tab testTab;
    @FXML
    private ScrollPane AWSHelpGettingStarted;
    @FXML
    private ScrollPane AWSHelpCLIInstallScrollPane;
    @FXML
    private ScrollPane AWSHelpConfigureAWSCLI;
    @FXML
    private ScrollPane AWSHelpFindAWSCLI;
    @FXML
    private ScrollPane AWSHelpConfigurePollyCLI;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebView gettingStartedBrowser = new WebView();
        WebView CLIInstallBrowser = new WebView();
        WebView configureCLIBrowser = new WebView();
        WebView findCLIBrowser = new WebView();
        WebView configurePollyBrowser = new WebView();
        
        WebEngine gettingStartedEngine = gettingStartedBrowser.getEngine();
        WebEngine CLIInstallEngine = CLIInstallBrowser.getEngine();
        WebEngine configureCLIEngine = configureCLIBrowser.getEngine();
        WebEngine findCLIEngine = findCLIBrowser.getEngine();
        WebEngine configurePollyEngine = configurePollyBrowser.getEngine();
        
        gettingStartedEngine.load(Strings.PROJECT_GETTING_STARTED_URL);
        CLIInstallEngine.load(Strings.PROJECT_INSTALL_AWS_CLI_URL);
        configureCLIEngine.load(Strings.PROJECT_CONFIGURE_AWS_CLI_URL);
        findCLIEngine.load(Strings.PROJECT_FINDING_AWS_CLI_URL);
        configurePollyEngine.load(Strings.PROJECT_CONFIGURING_POLLY_CLI_URL);
        
        AWSHelpGettingStarted.setContent(gettingStartedBrowser);
        AWSHelpCLIInstallScrollPane.setContent(CLIInstallBrowser);
        AWSHelpConfigureAWSCLI.setContent(configureCLIBrowser);
        AWSHelpFindAWSCLI.setContent(findCLIBrowser);
        AWSHelpConfigurePollyCLI.setContent(configurePollyBrowser);
    }    
    
}

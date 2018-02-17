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
package pollycli.StaticData;

import java.util.ResourceBundle;
import javafx.scene.image.Image;
import pollycli.DataStructures.SupportedLanguage;

/**
 *
 * @author Jacob Boone
 */
public class Paths {

    //FXML
    public static String MAINFXML = "/pollycli/FXML/MainPage.fxml";
    public static String ABOUTFXML = "/pollycli/FXML/AboutPage.fxml";
    public static String SETTINGSFXML = "/pollycli/FXML/SettingsPage.fxml";
    public static String AWSHELPFXML = "/pollycli/FXML/AWSHelpPage.fxml";
    
    //RESOURCES
    private static String PNG_BIRD = "/pollycli/Resources/bird.png";
    public static Image IMAGE_BIRD = new Image(PNG_BIRD);
    
    //BUNDLES
    
        //ENGLISH
    private static String LANG_EN_NAME = "English";
    public static String LANG_EN = "pollycli.Bundles.LangBundle_en";
    public static ResourceBundle ENG_BUNDLE = ResourceBundle.getBundle(Paths.LANG_EN);
    private static SupportedLanguage LANG_SUPPORTED_EN = new SupportedLanguage(LANG_EN_NAME, ENG_BUNDLE);
    
        //SPANISH
    private static String LANG_ES_NAME = "Spanish";
    public static String LANG_ES = "pollycli.Bundles.LangBundle_es";
    public static ResourceBundle ES_BUNDLE = ResourceBundle.getBundle(Paths.LANG_ES);
    private static SupportedLanguage LANG_SUPPORTED_ES = new SupportedLanguage(LANG_ES_NAME, ES_BUNDLE);
    
        //FRENCH
    private static String LANG_FR_NAME = "French";
    public static String LANG_FR = "pollycli.Bundles.LangBundle_fr";
    public static ResourceBundle FR_BUNDLE = ResourceBundle.getBundle(Paths.LANG_FR);
    private static SupportedLanguage LANG_SUPPORTED_FR = new SupportedLanguage(LANG_FR_NAME, FR_BUNDLE);

        //CHINESE
    private static String LANG_CH_NAME = "Chinese";
    public static String LANG_CH = "pollycli.Bundles.LangBundle_ch";
    public static ResourceBundle CH_BUNDLE = ResourceBundle.getBundle(Paths.LANG_CH);
    private static SupportedLanguage LANG_SUPPORTED_CH = new SupportedLanguage(LANG_CH_NAME, CH_BUNDLE);
    
    //TO ADD NEW LANGUAGE CREATE PROP FILE AND THEN ADD TO BUNDLES & THIS ARRAY
    public static SupportedLanguage[] SUPPORTED_LANGUAGES = {LANG_SUPPORTED_EN, LANG_SUPPORTED_ES, LANG_SUPPORTED_FR, LANG_SUPPORTED_CH};
    
    //Client Side Properties
    public static String CLIENT_PROPERTIES = "config.properties";
    
    public Paths() {
    }
}

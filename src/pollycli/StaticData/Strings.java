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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Jacob Boone
 */
public class Strings {

    private static final String VERSION = "0.2.2";
    private static final String APPNAME = "PollyCLI";
    private static final String TITLEBAR = APPNAME + " " + VERSION;
    public static final String MainPageTitle = TITLEBAR;
    public static final String SettingsPageTitle = TITLEBAR + " - Settings";
    public static final String SettingsAWSHelpTitle = TITLEBAR + " - AWS Configuration Help";
    public static final String AboutPageTitle = TITLEBAR + " - About";
    
    public static String SETTINGS_OUTPUT = "OUTPUT";
    public static String SETTINGS_AWSCMD = "AWSDIRECTORY";
    public static String SETTINGS_NUMREQS = "REQUESTS";
    public static String SETTINGS_SPEAKER = "NARRATOR";
    public static String SETTINGS_STATEMENT_INITIAL = "STATEMENT_INITIAL";
    public static String SETTINGS_STATEMENT_PART01 = "STATEMENT_PART01";
    public static String SETTINGS_STATEMENT_PART02 = "STATEMENT_PART02";
    public static String SETTINGS_STATEMENT_PART03 = "STATEMENT_PART03";
    public static String SETTINGS_STATEMENT_PART04 = "STATEMENT_PART04";
    public static String SETTINGS_STATEMENT_PART05 = "STATEMENT_PART05";
    public static String SETTINGS_STATEMENT_PART06 = "STATEMENT_PART06";
    
    public static String SETTINGS_STATEMENT_INITIAL_CONTENTS = "cmd.exe /C ";
    public static String SETTINGS_STATEMENT_PART01_CONTENTS = "\"";
    public static String SETTINGS_STATEMENT_PART02_CONTENTS = " polly synthesize-speech --output-format=\"";
    public static String SETTINGS_STATEMENT_PART03_CONTENTS = "\" --text=\"";
    public static String SETTINGS_STATEMENT_PART04_CONTENTS = "\" --voice-id=\"";
    public static String SETTINGS_STATEMENT_PART05_CONTENTS = "\" \"";
    public static String SETTINGS_STATEMENT_PART06_CONTENTS = "\"";
    
    
    //CSS STUFF
    public static String CSS_FILE_PATH = "/pollycli/Style/style.css";
    public static String FILE_DISPLAY_ITEM_UNPROCESSED_CSS = "fileDisplayItem";
    public static String FILE_DISPLAY_ITEM_PROCESSED_CSS = "fileDisplayItemProcessed";
    public static String FILE_DISPLAY_VBOX_CSS = "fileDisplayVBox";
    
    //PollyStatement Stuff
    public static String[] TRACKED_STATEMENT_PROPERTY_TARGETS = {SETTINGS_OUTPUT, SETTINGS_AWSCMD, SETTINGS_NUMREQS, SETTINGS_SPEAKER};
    
    //Narration Stuff
    public static String[] NARRATORS = {"Nicole", "Russell", "Amy", "Brian", "Emma", "Joanna", "Matthew", "Salli", "Justin",
            "Kendra", "Joey", "Kimberly", "Ivy"};
    
    //File Type Stuff
    public static String[] FILE_TYPES = {"mp3", "ogg_vorbis", "pcm"};
    
    public Charset UTF = Charset.defaultCharset();
    
    //PROJECT URL(S) & HELP FILE URL(S)
    public static final String PROJECT_INSTALL_AWS_CLI_URL = "https://github.com/Jboonie/PollyCLI/wiki/03-Installing-Amazon-CLI";
    public static final String PROJECT_CONFIGURE_AWS_CLI_URL = "https://github.com/Jboonie/PollyCLI/wiki/04-Configuring-Amazon-CLI";
    public static final String PROJECT_WEBSITE_URL = "https://github.com/Jboonie/PollyCLI";
    public static final String PROJECT_GETTING_STARTED_URL = "https://github.com/Jboonie/PollyCLI/wiki/02-Getting-Started";
    public static final String PROJECT_FINDING_AWS_CLI_URL = "https://github.com/Jboonie/PollyCLI/wiki/05-Finding-the-Amazon-CLI-Install-Directory";
    public static final String PROJECT_CONFIGURING_POLLY_CLI_URL = "https://github.com/Jboonie/PollyCLI/wiki/06-Configuring-PollyCLI";
    
    //Supported Input Types
    private static final String[] SUPPORTED_INPUT_ARRAY = {".txt"};
    public static final ArrayList<String> SUPPORTED_INPUT = new ArrayList<>(Arrays.asList(SUPPORTED_INPUT_ARRAY));
    public static final String FILE_EXTENSION_SEPERATOR = ".";
    public static final String FILE_DEFAULT_BROWSE_DIRECTORY = "C:\\";
    
    public static final String NEW_LINE = "\n";
    
    public Strings() {
    }    
    
    //REFERENCE MATERIAL
    /*
    public static String COMMAND_ORDER = "SETTINGS_STATEMENT_INITIAL_CONTENTS + SETTINGS_STATEMENT_PART01_CONTENTS + SETTINGS_AWSCMD (CONTENTS OF) +"
            + " SETTINGS_STATEMENT_PART02_CONTENTS + SETTINGS_OUTPUT(CONTENTS OF) + SETTINGS_STATEMENT_PART03_CONTENTS + TEXT + " +
            "SETTINGS_STATEMENT_PART04_CONTENTS + VOICE + SETTINGS_STATEMENT_PART05_CONTENTS + FILE DATA";
    */
}

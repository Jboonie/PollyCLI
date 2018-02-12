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
package pollycli.DataStructures;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pollycli.StaticData.Strings;

/**
 *
 * @author Jacob Boone
 */
public class FileDisplayItem extends HBox{
    
    private Label label;
    private boolean isComplete = false;

    public FileDisplayItem(String data) {
        label = new Label(data);
        this.getStylesheets().clear();
        this.getStylesheets().add(Strings.CSS_FILE_PATH);
        System.out.println(this.getStylesheets().toString());
        this.getStyleClass().add(Strings.FILE_DISPLAY_ITEM_UNPROCESSED_CSS);
        this.getChildren().add(label);
    }
    
    public void toggleStatus(){
        if(isComplete){
            this.getStyleClass().clear();
            this.getStyleClass().add(Strings.FILE_DISPLAY_ITEM_UNPROCESSED_CSS);
            isComplete = false;
        }
        else{
            this.getStyleClass().clear();
            this.getStyleClass().add(Strings.FILE_DISPLAY_ITEM_PROCESSED_CSS);
            isComplete = true;
        }
    }
    
    public boolean isComplete(){
        return isComplete;
    }
    
    
}

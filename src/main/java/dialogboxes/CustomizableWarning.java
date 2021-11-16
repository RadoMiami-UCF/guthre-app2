/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CustomizableWarning {

    @FXML
    private Text warningField;

    public void setWarningField(String warningString) {
        //Set the text of warningField to "Warning: [warningString]".
    }

    @FXML
    private void closeDialogBox(ActionEvent event) {
        //Simply close the dialog box.
    }
}

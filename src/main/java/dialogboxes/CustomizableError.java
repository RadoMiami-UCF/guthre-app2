/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomizableError {

    @FXML
    private Button continueButton;

    @FXML
    private Text errorField;

    public void setErrorField(String errorString) {
        //Set the text of errorField to "Error: [errorString]".
        errorField.setText("Error: " + errorString);
    }

    @FXML
    private void closeDialogBox() {
        //Simply close the dialog box.
        var stage = (Stage) continueButton.getScene().getWindow();
        stage.close();
    }
}

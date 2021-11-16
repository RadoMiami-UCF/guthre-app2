/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.InventoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class SearchBox {
    @FXML
    private ChoiceBox<InventoryItem> searchOptionBox;

    @FXML
    private TextField searchTermField;

    @FXML
    private void initialize() {
        //Set the options for searchOptionBox to "Name" and "Serial Number".
    }

    @FXML
    private void closeDialogBox() {
        //Simply close the dialog box.
    }

    @FXML
    private void searchList() {
        //If an option has been selected in searchOptionBox, then...
        //If "Name" was selected, filter out any items whose name doesn't include the value of searchTermField.
        //If "Serial Number" was selected, then check if the value of searchTermField fits the format A-XXX-XXX-XXX.
        //If it does, filter out all items except the item that has that serial number (if applicable).
    }
}

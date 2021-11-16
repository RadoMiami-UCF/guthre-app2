/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.InventoryItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;

public class AddItem {

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField serialNumberField;

    @FXML
    private TextField valueField;

    private LinkedHashMap<String, InventoryItem> itemLinkedHashMap;

    public void setItemLinkedHashMap(LinkedHashMap<String, InventoryItem> itemLinkedHashMap) {
        //Set this.itemLinkedHashMap to itemLinkedHashMap.
    }

    @FXML
    private void addItem(ActionEvent event) {
        /*If all the fields are filled out and valid, add an entry to itemLinkedHashMap with the key of the given serial
        number and an InventoryItem with the correct name, serial number, and value.*/
        //Then, close the dialog box.
    }

    @FXML
    private void closeDialogBox(ActionEvent event) {
        //Simply close the dialog box.
    }
}

/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.InventoryItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.LinkedHashMap;

public class DeleteAllItemsConfirm {

    @FXML
    private Button cancelButton;

    private LinkedHashMap<String, InventoryItem> itemLinkedHashMap;

    public void setItemLinkedHashMap(LinkedHashMap<String, InventoryItem> itemLinkedHashMap) {
        //Set this.itemLinkedHashMap to itemLinkedHashMap.
    }

    @FXML
    private void closeDialogBox(ActionEvent event) {
        //Simply close the dialog box.
    }

    @FXML
    private void deleteAllItems(ActionEvent event) {
        //Delete all the items in the linked hash map.
        //Then, close the dialog box.
    }
}

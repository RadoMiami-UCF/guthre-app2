/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.InventoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.LinkedHashMap;

public class DeleteItem {
    @FXML
    private ComboBox<InventoryItem> itemComboBox;

    public void setItemLinkedHashMap(LinkedHashMap<String, InventoryItem> itemLinkedHashMap) {
        //Set this.itemLinkedHashMap to itemLinkedHashMap.
    }

    @FXML
    private void closeDialogBox() {
        //Simply close the dialog box.
    }

    @FXML
    private void deleteItem() {
        /*If an item (represented by serial number) has been selected in the itemComboBox, then remove that item from
        the list.*/
    }
}

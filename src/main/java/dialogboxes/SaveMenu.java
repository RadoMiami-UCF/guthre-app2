/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.InventoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.LinkedHashMap;

public class SaveMenu {
    @FXML
    private TextField fileTextField;
    private File fileToSaveTo;

    public void setItemLinkedHashMap(LinkedHashMap<String, InventoryItem> itemLinkedHashMap) {
        //Set this.itemLinkedHashMap to itemLinkedHashMap.
    }

    @FXML
    private void selectFile() {
        //Open a fileChooser with .json, .html, and .txt as valid file types.
        //Set fileToSaveTo to the fileChooser's resulting file.
    }

    @FXML
    private void saveList() {
        //If a file has been chosen, check the extension.
        //If it's .json, save the file as a json of the linked hash map.
        //If it's .html, save the file as a html table of the linked hash map.
        //If it's .txt, save the file as a TSV of the linked hash map.
        //If it's anything else, display an error.
    }

    @FXML
    private void closeDialogBox() {
        //Simply close the dialog box.
    }
}

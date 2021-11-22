/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.FXMLController;
import baseline.InventoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddItem extends ListModifyingDialogBox {

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField serialNumberField;

    @FXML
    private TextField valueField;

    @FXML
    private void addItem() {
        //If all the fields are filled out and valid,
        if(itemNameField.getText().length() >= 2) {
            if(itemNameField.getText().length() <= 256) {
                if(valueField.getText().matches("\\$\\d+\\.\\d{2}")) {
                    if(serialNumberField.getText().matches("\\p{IsAlphabetic}-[\\p{IsAlphabetic}\\d]{3}" +
                            "-[\\p{IsAlphabetic}\\d]{3}-[\\p{IsAlphabetic}\\d]{3}")) {
                        if(!itemHashMap.containsKey(serialNumberField.getText().toUpperCase())) {
                            /*add an entry to itemHashMap with the key of the given serial number and an
                            InventoryItem with the correct name, serial number, and value.*/
                            itemHashMap.put(serialNumberField.getText().toUpperCase(),
                                    new InventoryItem(valueField.getText(), itemNameField.getText(),
                                            serialNumberField.getText().toUpperCase()));
                            //Then, refresh the TableView so that it's updated with the new item.
                            tableView.refresh();
                            //Finally, close the dialog box.
                            closeDialogBox();
                        } else {
                            FXMLController.openErrorBox("That serial number is already in use!");
                        }
                    } else {
                        FXMLController.openErrorBox("Serial number is not formatted correctly! " +
                                "(Should be in the format A-XXX-XXX-XXX)");
                    }
                } else {
                    FXMLController.openErrorBox("Value is not formatted correctly! " +
                            "(Should be formatted like $0.12 or $12.34)");
                }
            } else {
                FXMLController.openErrorBox("Name is too long!");
            }
        } else {
            FXMLController.openErrorBox("Name is too short!");
        }
        //Otherwise, display an appropriate error.
    }
}

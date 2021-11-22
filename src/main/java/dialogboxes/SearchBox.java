/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.FXMLController;
import baseline.InventoryItem;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class SearchBox extends ListModifyingDialogBox {
    private static final Pattern VALID_SERIAL_NUMBER = Pattern.compile("\\p{IsAlphabetic}-[\\p{IsAlphabetic}\\d]{3}-" +
            "[\\p{IsAlphabetic}\\d]{3}-[\\p{IsAlphabetic}\\d]{3}");
    private FilteredList<Map.Entry<? extends String, ? extends InventoryItem>> filteredItemList;

    @FXML
    private ChoiceBox<String> searchOptionBox;

    @FXML
    private TextField searchTermField;

    public void setFilteredList(FilteredList<Map.Entry<? extends String, ? extends InventoryItem>> filteredItemList) {
        //Set this.filteredItemList to filteredItemList.
        this.filteredItemList = filteredItemList;
    }

    @FXML
    private void initialize() {
        //Set the options for searchOptionBox to "Name" and "Serial Number".
        searchOptionBox.getItems().setAll("Name", "Serial Number");
    }

    @FXML
    private void searchList() {
        //If an option has been selected in searchOptionBox, then...
        if(!searchOptionBox.getSelectionModel().isEmpty()) {
            if("Name".equals(searchOptionBox.getSelectionModel().getSelectedItem())) {
                //If "Name" was selected, filter out any items whose name doesn't include the value of searchTermField.
                filteredItemList.setPredicate(t -> (t.getValue().getName().toLowerCase(Locale.US)
                        .contains(searchTermField.getText().toLowerCase(Locale.US))));
                //Finally, close the window.
                closeDialogBox();
            } else {
                //If "Serial Number" was selected, then check if the value of searchTermField fits the format A-XXX-XXX
                //-XXX.
                if(VALID_SERIAL_NUMBER.matcher(searchTermField.getText()).matches()) {
                    //If it does, filter out all items except the item that has that serial number (if applicable).
                    filteredItemList.setPredicate(t -> (t.getKey().equalsIgnoreCase(searchTermField.getText())));
                    //Finally, close the window.
                    closeDialogBox();
                } else {
                    //Otherwise, show an error saying the serial number was invalid.
                    FXMLController.openErrorBox("Serial number given was invalid!");
                }
            }
        } else {
            //Otherwise, show an error saying that no option was chosen.
            FXMLController.openErrorBox("No option was chosen!");
        }
    }
}

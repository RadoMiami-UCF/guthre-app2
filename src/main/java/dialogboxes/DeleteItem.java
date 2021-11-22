/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.InventoryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.util.Map;

public class DeleteItem extends ListModifyingDialogBox {
    private final ObservableList<String> observableItemList
            = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> itemComboBox;

    @Override
    public void setupDialogBox(Map<String, InventoryItem> itemLinkedHashMap,
                               TableView<Map.Entry<? extends String, ? extends InventoryItem>> tableView) {
        super.setupDialogBox(itemLinkedHashMap, tableView);
        observableItemList.setAll(itemLinkedHashMap.keySet());
        itemComboBox.setItems(observableItemList);
    }

    @FXML
    private void deleteItem() {
        /*If an item (represented by serial number) has been selected in the itemComboBox, then remove that item from
        the list.*/
        if(itemComboBox.getSelectionModel().getSelectedItem() != null) {
            //If that's the case, call observableItemList.deleteItemFromList(itemIndex).
            itemHashMap.remove(observableItemList.get(itemComboBox.getSelectionModel().getSelectedIndex()));
            //Then, refresh tableView to make sure that it displays properly when the dialog box closes.
            tableView.refresh();
            //Then, close the dialog box.
            closeDialogBox();
        }
    }
}

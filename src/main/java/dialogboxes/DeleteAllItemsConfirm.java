/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import javafx.fxml.FXML;

public class DeleteAllItemsConfirm extends ListModifyingDialogBox {

    @FXML
    private void deleteAllItems() {
        //Delete all the items in the linked hash map.
        itemHashMap.clear();
        //Then, update the tableView.
        tableView.refresh();
        //Then, close the dialog box.
        closeDialogBox();
    }
}

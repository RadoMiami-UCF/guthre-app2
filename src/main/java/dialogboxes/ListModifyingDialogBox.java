package dialogboxes;

import baseline.InventoryItem;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Map;

public class ListModifyingDialogBox {
    protected TableView<Map.Entry<? extends String, ? extends InventoryItem>> tableView;
    protected ObservableMap<String, InventoryItem> itemHashMap;

    @FXML
    private Button cancelButton;

    protected ListModifyingDialogBox() {}

    @FXML
    protected void closeDialogBox() {
        //Simply close the dialog box.
        var stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setupDialogBox(Map<String, InventoryItem> itemLinkedHashMap,
                               TableView<Map.Entry<? extends String, ? extends InventoryItem>> tableView) {
        //Set this.itemHashMap to itemHashMap and this.tableView to tableView.
        this.itemHashMap = (ObservableMap<String, InventoryItem>) itemLinkedHashMap;
        this.tableView = tableView;
    }
}

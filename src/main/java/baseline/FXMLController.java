/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package baseline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;

public class FXMLController {

    @FXML
    private TableColumn<InventoryItem, String> nameCol;

    @FXML
    private TableColumn<InventoryItem, String> serialNumberCol;

    @FXML
    private TableView<InventoryItem> tableView;

    @FXML
    private TableColumn<InventoryItem, Double> valueCol;

    private final LinkedHashMap<String, InventoryItem> itemLinkedHashMap = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        //First, create an observable list through itemLinkedHashMap.values().
        //Then, set tableView's list to that observable list.
        /*Then, set up each column's cell factories and cell value factories so that each cell is modifiable by a
        text field.*/
        /*Furthermore, set the cell value factory for the value column to only save if it's a decimal, for the name to
        only save if the given name is 2-256 characters, and for the serial number to only save if it's in the format
        A-XXX-XXX-XXX and isn't already in use.*/
    }

    @FXML
    void clearSearchTerms() {
        //Unfilter the list, undoing any searches done.
    }

    @FXML
    void loadItemsFromFile() {
        //Open a fileChooser that can only accept .fxml, .txt, and .json files.
        //When a file is chosen, try to pull the items from the file.
        //If the file seems to be incorrectly formatted, display an error.
    }

    private void openDialogBox(String pathToFXML, String dialogBoxName) {
        //Opens a dialog box with the specified FXML file and name.
        try {
            var loader = new FXMLLoader(getClass().getResource(pathToFXML));
            Parent root = loader.load();

            var scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

            var stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle(dialogBoxName);
            stage.setScene(scene);
            stage.show();
            //Then, set the linked hash map of the FXML controller to itemLinkedHashMap.
        } catch (IOException exception) {
            //Log any IOExceptions caused by an invalid pathToFXML being given.
        }
    }
}

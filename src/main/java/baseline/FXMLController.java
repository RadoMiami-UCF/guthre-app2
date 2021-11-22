/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package baseline;

import dialogboxes.ListModifyingDialogBox;
import dialogboxes.CustomizableError;
import dialogboxes.LoadMenu;
import dialogboxes.SearchBox;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class FXMLController {
    private static final int MIN_NAME_SIZE = 2;
    private static final int MAX_NAME_SIZE = 256;
    private static final String STYLESHEET_FILE_NAME = "styles.css";
    private static final Pattern VALUE_PATTERN = Pattern.compile("\\$\\d+\\.\\d{2}");
    private static final Pattern SERIAL_NUMBER_PATTERN = Pattern.compile("\\p{IsAlphabetic}-[\\p{IsAlphabetic}\\d]{3}" +
            "-[\\p{IsAlphabetic}\\d]{3}-[\\p{IsAlphabetic}\\d]{3}");

    @FXML
    private TableColumn<Map.Entry<? extends String, ? extends InventoryItem>, String> nameCol;

    @FXML
    private TableColumn<Map.Entry<? extends String, ? extends InventoryItem>, String> serialNumberCol;

    @FXML
    private TableView<Map.Entry<? extends String, ? extends InventoryItem>> tableView;

    @FXML
    private TableColumn<Map.Entry<? extends String, ? extends InventoryItem>, String> valueCol;

    private final ObservableMap<String, InventoryItem> itemHashMap = FXCollections.observableHashMap();
    private final ObservableList<Map.Entry<? extends String, ? extends InventoryItem>> observableItemList
            = FXCollections.observableArrayList();
    private final FilteredList<Map.Entry<? extends String, ? extends InventoryItem>> filteredItemList
            = new FilteredList<>(observableItemList);
    private final SortedList<Map.Entry<? extends String, ? extends InventoryItem>> sortedItemList
            = new SortedList<>(filteredItemList);

    @FXML
    public void initialize() {
        //Being real, I don't even myself know what this code is for. All I know is that it makes the Java gods happy.
        itemHashMap.addListener((MapChangeListener<String, InventoryItem>) change
                -> observableItemList.setAll(change.getMap().entrySet()));
        /*Then, set up each column's cell factories and cell value factories so that each cell is modifiable by a
        text field and reflects their respective field.*/
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //Side note: Values are represented as strings and not decimals so that they would play nice with text fields.
        //That also probably fixed a future potential bug brought on due to floating point imprecision.
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        serialNumberCol.setCellValueFactory(cellData -> cellData.getValue().getValue().serialNumberProperty());
        valueCol.setCellValueFactory(cellData -> cellData.getValue().getValue().valueProperty());
        //Finally, set the edit commits so that they only accept certain kinds of data.
        setupColumnEditBehavior();
        valueCol.setComparator((String v1, String v2) -> {
            var v1Val = Double.parseDouble(v1.substring(1));
            var v2Val = Double.parseDouble(v2.substring(1));
            return Double.compare(v1Val, v2Val);
        });
        //Then, bind the sortedItemList's comparator to the tableView's.
        sortedItemList.comparatorProperty().bind(tableView.comparatorProperty());
        //Finally, set tableView's list to sortedItemList.
        tableView.setItems(sortedItemList);
    }

    //Long lambdas await yee who read this method!
    private void setupColumnEditBehavior() {
        //Only save commits to the name column if they're between 2-256 characters in length.
        nameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Map.Entry<? extends String, ? extends InventoryItem>, String> t) -> {
                    if(t.getNewValue().length() >= MIN_NAME_SIZE) {
                        if(t.getNewValue().length() <= MAX_NAME_SIZE) {
                            (t.getTableView().getItems().get(t.getTablePosition().getRow())).getValue()
                                    .setName(t.getNewValue());
                        } else {
                            openErrorBox("Given name is too long!");
                            //I know it's bad practice to use refresh, but I really don't want to fiddle with the
                            //Java spaghetti to make my own cell class again.
                            tableView.refresh();
                        }
                    } else {
                        openErrorBox("Given name is too short!");
                        tableView.refresh();
                    }
                });
        //Only save commits to the serial number column if it's in the format A-XXX-XXX-XXX and is not already in use.
        serialNumberCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Map.Entry<? extends String, ? extends InventoryItem>, String> t) -> {
                    if(SERIAL_NUMBER_PATTERN.matcher(t.getNewValue()).matches()) {
                        if(!itemHashMap.containsKey(t.getNewValue().toUpperCase(Locale.US))) {
                            //Causes the entry to get moved to the last index when serial number is successfully
                            //changed... too bad!
                            var tempItem = itemHashMap.get(t.getOldValue());
                            itemHashMap.remove(t.getOldValue());
                            tempItem.setSerialNumber(t.getNewValue().toUpperCase(Locale.US));
                            itemHashMap.put(t.getNewValue().toUpperCase(Locale.US), tempItem);
                        } else {
                            openErrorBox("Given serial number is already in use!");
                        }
                    } else {
                        openErrorBox("Given serial number is not formatted correctly!");
                    }
                    tableView.refresh();
                });
        //Only save commits to the value column if it's in the format $#.##.
        valueCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Map.Entry<? extends String, ? extends InventoryItem>, String> t) -> {
                    if(VALUE_PATTERN.matcher(t.getNewValue()).matches()) {
                        (t.getTableView().getItems().get(t.getTablePosition().getRow())).getValue()
                                .setValue(t.getNewValue());
                    } else {
                        openErrorBox("Given value is not formatted correctly!");
                        tableView.refresh();
                    }
                });
    }

    @FXML
    private void addItem() {
        //Open the dialog box for adding an item.
        openDialogBox("/dialogboxes/addItem.fxml", "Add An Item");
    }

    @FXML
    private void deleteItem() {
        //If there's an item in the list, open the dialog box for deleting an item.
        if(!observableItemList.isEmpty()) {
            openDialogBox("/dialogboxes/deleteItem.fxml", "Delete An Item");
        } else {
            //Otherwise, show an error saying that there aren't any items to delete.
            openErrorBox("There aren't any items to delete!");
        }
    }

    @FXML
    private void deleteAllItems() {
        //If there's an item in the list, open the dialog box for deleting all the items.
        if(!observableItemList.isEmpty()) {
            openDialogBox("/dialogboxes/deleteAllItemsConfirm.fxml", "Delete All Items");
        } else {
            //Otherwise, show an error saying that there aren't any items to delete.
            openErrorBox("There aren't any items to delete!");
        }
    }

    @FXML
    private void loadList() {
        //Call a loadMenu based on itemHashMap's loadList method.
        var loadMenu = new LoadMenu(itemHashMap);
        loadMenu.loadList();
    }

    @FXML
    private void saveList() {
        //If there's an item in the list, open the dialog box for saving the list
        if(!observableItemList.isEmpty()) {
            openDialogBox("/dialogboxes/saveMenu.fxml", "Save List");
        } else {
            //Otherwise, show an error saying that there aren't any items to save.
            openErrorBox("There aren't any items to save!");
        }
    }

    @FXML
    private void searchItems() {
        //If there's an item in the list, open the dialog box for searching for items.
        if(!observableItemList.isEmpty()) {
            try {
                var loader = new FXMLLoader(getClass().getResource("/dialogboxes/searchBox.fxml"));
                Parent root = loader.load();
                ((ListModifyingDialogBox) loader.getController()).setupDialogBox(itemHashMap, tableView);
                ((SearchBox) loader.getController()).setFilteredList(filteredItemList);

                var scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLESHEET_FILE_NAME))
                        .toExternalForm());

                var stage = new Stage();
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.setTitle("Set Search Terms");
                stage.setScene(scene);
                stage.show();
                //Then, set the linked hash map of the FXML controller to itemHashMap.
            } catch (IOException e) {
                //Log any IOExceptions caused by an invalid pathToFXML being given.
                var logger = Logger.getLogger("DialogBoxIOExceptionLogger");
                logger.log(Level.SEVERE, "Dialog box could not be opened!", e);
            }
        } else {
            //Otherwise, show an error saying that there aren't any items to search for.
            openErrorBox("There aren't any items to search for!");
        }
    }

    @FXML
    private void clearSearchTerms() {
        //Remove the filter of the list, undoing any searches done.
        filteredItemList.setPredicate(t -> true);
    }

    private void openDialogBox(String pathToFXML, String dialogBoxName) {
        //Opens a dialog box with the specified FXML file and name.
        try {
            var loader = new FXMLLoader(getClass().getResource(pathToFXML));
            Parent root = loader.load();
            ((ListModifyingDialogBox) loader.getController()).setupDialogBox(itemHashMap, tableView);

            var scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLESHEET_FILE_NAME))
                    .toExternalForm());

            var stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle(dialogBoxName);
            stage.setScene(scene);
            stage.show();
            //Then, set the linked hash map of the FXML controller to itemHashMap.
        } catch (IOException e) {
            //Log any IOExceptions caused by an invalid pathToFXML being given.
            var logger = Logger.getLogger("DialogBoxIOExceptionLogger");
            logger.log(Level.SEVERE, "Dialog box could not be opened!", e);
        }
    }

    public static void openErrorBox(String errorString) {
        try {
            var loader = new FXMLLoader(FXMLController.class.getResource("/dialogboxes/customizableError.fxml"));
            Parent root = loader.load();
            ((CustomizableError) loader.getController()).setErrorField(errorString);

            var scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(FXMLController.class.getResource(STYLESHEET_FILE_NAME))
                    .toExternalForm());

            var stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Error");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            var logger = Logger.getLogger("IOExceptionLogger");
            logger.log(Level.SEVERE, "Ironically enough, error occurred while opening error box!", e);
        }
    }
}

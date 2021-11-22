/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package dialogboxes;

import baseline.FXMLController;
import baseline.InventoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hildan.fxgson.FxGson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveMenu extends ListModifyingDialogBox {
    @FXML
    private TextField fileTextField;

    private File file;

    @FXML
    private void selectFile() {
        //Open a fileChooser with .json, .html, and .txt as valid file types.
        var chooser = new FileChooser();
        chooser.setTitle("Select File to Save To");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tab-Separated Values", "*.txt"),
                new FileChooser.ExtensionFilter("JavaScript Object Notation", "*.json"),
                new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html"));
        //Set fileToSaveTo to the fileChooser's resulting file.
        file = chooser.showSaveDialog(new Stage());
        if(file != null) {
            fileTextField.setText(file.toString());
        } else {
            fileTextField.setText("");
        }
    }

    @FXML
    private void saveList() {
        //If a file has been chosen, check the extension.
        if(file != null) {
            try {
                switch (file.getName().substring(file.getName().lastIndexOf(".") + 1)) {
                    //If it's .txt, save the file as a TSV of the hash map.
                    case "txt" -> saveAsTSV();
                    //If it's .json, save the file as a json of the hash map.
                    case "json" -> saveAsJSON();
                    //If it's .html, save the file as a html table of the hash map.
                    case "html" -> saveAsHTML();
                    //If it's anything else, display an error.
                    default -> FXMLController.openErrorBox("File type selected is not supported!");
                }
            } catch (IOException e) {
                var logger = Logger.getLogger("IOExceptionLogger");
                logger.log(Level.SEVERE, "IOException somehow occurred!", e);
                e.printStackTrace();
            }
        } else {
            FXMLController.openErrorBox("No file was selected!");
        }
    }

    private void saveAsTSV() throws IOException {
        try (var j = Files.newBufferedWriter(file.toPath())){
            //To save as a TSV, first write the header.
            j.write("Serial Number\tName\tValue");
            //Then, write each individual object, line by line.
            for(InventoryItem item : itemHashMap.values()) {
                j.write("\n" + item.getSerialNumber() + "\t" + item.getName() + "\t" + item.getValue());
            }
            //Afterwards, close the window.
            closeDialogBox();
        }
    }

    private void saveAsJSON() throws IOException {
        //To save as .json, simply use gson.toJson with an FxGson to convert the hash map to json.
        var gson = FxGson.create();
        var mapAsJson = gson.toJson(itemHashMap);
        Files.writeString(file.toPath(), mapAsJson);
        //Afterwards, close the window.
        closeDialogBox();
    }

    private void saveAsHTML() throws IOException {
        try (var j = Files.newBufferedWriter(file.toPath())){
            //To save as html, first write the beginning of the html file and the header of the table.
            j.write("<html>\n<body>\n\n<table>\n");
            j.write("  <tr>\n    <th>Serial Number</th>\n    <th>Name</th>\n    <th>Value</th>\n  </tr>");
            //Then, write each individual object, line by line.
            for(InventoryItem item : itemHashMap.values()) {
                j.write("\n  <tr>\n    <td>" + item.getSerialNumber() + "</td>\n    <td>" + item.getName()
                + "</td>\n    <td>" + item.getValue() + "</td>\n  </tr>");
            }
            //Then, write the end of the file.
            j.write("\n</table>\n\n</body>\n</html>");
            //Afterwards, close the window.
            closeDialogBox();
        }
    }
}

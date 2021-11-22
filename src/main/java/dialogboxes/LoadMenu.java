package dialogboxes;

import baseline.FXMLController;
import baseline.InventoryItem;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableMap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hildan.fxgson.FxGson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadMenu {
    private static final int VALUE_INDEX_TSV = 2;
    private static final int VALUE_INDEX_HTML = 2;
    private static final int NAME_INDEX_HTML = 1;
    private final ObservableMap<String, InventoryItem> itemHashMap;
    private File file;

    public LoadMenu(ObservableMap<String, InventoryItem> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    public void loadList() {
        //First, open a fileChooser that only accepts .txt, .json, and .html.
        var chooser = new FileChooser();
        chooser.setTitle("Select File to Load From");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Valid Formats", "*.txt", "*.json", "*.html"),
                new FileChooser.ExtensionFilter("Tab-Separated Values", "*.txt"),
                new FileChooser.ExtensionFilter("JavaScript Object Notation", "*.json"),
                new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html"));
        //Set file to the fileChooser's resulting file.
        file = chooser.showOpenDialog(new Stage());
        //If a file has been chosen, check the extension.
        if(file != null) {
            try {
                switch (file.getName().substring(file.getName().lastIndexOf(".") + 1)) {
                    //If it's .txt, read the file as a TSV.
                    case "txt" -> {
                        itemHashMap.clear();
                        loadFromTSV();
                    }
                    //If it's .json, read the file as a json.
                    case "json" -> {
                        itemHashMap.clear();
                        loadFromJSON();
                    }
                    //If it's .html, read the file as a html.
                    case "html" -> {
                        itemHashMap.clear();
                        loadFromHTML();
                    }
                    //If it's anything else, display an error.
                    default -> FXMLController.openErrorBox("File type selected is not supported!");
                }
            } catch (IOException e) {
                var logger = Logger.getLogger("IOException Logger");
                logger.log(Level.SEVERE, "IOException somehow occurred!", e);
            }
        }
    }

    private void loadFromTSV() throws IOException {
        try (var j = Files.newBufferedReader(file.toPath())) {
            //To read from a TSV, read each line (minus the header) and translate it into an item.
            //The first line needs to be wasted since it's just the header.
            var k = j.readLine();
            while((k = j.readLine()) != null) {
                var tempStrings = k.split("\t");
                itemHashMap.put(tempStrings[0], new InventoryItem(tempStrings[VALUE_INDEX_TSV], tempStrings[1],
                        tempStrings[0]));
            }
        }
    }

    private void loadFromJSON() throws IOException {
        //To load from .json, simply use gson.fromJson with an FxGson to convert the json into a hash map.
        var gson = FxGson.create();
        var jsonString = Files.readString(file.toPath());
        var tempType = new TypeToken<Map<String, InventoryItem>>(){}.getType();
        itemHashMap.putAll(gson.fromJson(jsonString, tempType));
    }

    private void loadFromHTML() throws IOException {
        //To load from .html, find every instance of a table entry and extract the values from it.
        var htmlString = Files.readString(file.toPath());
        var tableDoc = Jsoup.parse(htmlString);
        var table = tableDoc.select("table").get(0);
        var tableRows = table.select("tr");
        for(Element tableRow : tableRows) {
            var tableRowEntries = tableRow.select("td");
            if(!tableRowEntries.isEmpty()) {
                itemHashMap.put(tableRowEntries.get(0).text(),
                        new InventoryItem(tableRowEntries.get(VALUE_INDEX_HTML).text(),
                        tableRowEntries.get(NAME_INDEX_HTML).text(), tableRowEntries.get(0).text()));
            }
        }
    }
}

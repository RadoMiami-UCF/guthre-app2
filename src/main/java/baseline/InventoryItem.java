/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package baseline;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InventoryItem {
    private StringProperty value = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty serialNumber = new SimpleStringProperty();

    public InventoryItem(String value, String name, String serialNumber) {
        //Set each variable to the corresponding value given.
        this.value.set(value);
        this.name.set(name);
        this.serialNumber.set(serialNumber);
    }

    public void setValue(String value) {
        //Set this.value's value to value.
        this.value.set(value);
    }

    public void setName(String name) {
        //Set this.name's value to name.
        this.name.set(name);
    }

    public void setSerialNumber(String serialNumber) {
        //Set this.serialNumber's value to serialNumber.
        this.serialNumber.set(serialNumber);
    }

    public StringProperty valueProperty() {
        //Return value.
        return value;
    }

    public StringProperty nameProperty() {
        //Return name.
        return name;
    }

    public StringProperty serialNumberProperty() {
        //Return serialNumber.
        return serialNumber;
    }

    public String getValue() {
        //Return value's value.
        return value.get();
    }

    public String getName() {
        //Return name's value.
        return name.get();
    }

    public String getSerialNumber() {
        //Return serialNumber's value.
        return serialNumber.get();
    }
}

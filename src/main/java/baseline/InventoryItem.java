/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Kimari Guthre
 */
package baseline;

public class InventoryItem {
    private double value;
    private String name;
    private String serialNumber;

    public InventoryItem(double value, String name, String serialNumber) {
        //Set each variable to the corresponding value given.
    }

    public void setValue(double value) {
        //If value => 0, set this.value to value.
    }

    public void setName(String name) {
        //If name.length() => 2 && <= 256, set this.name to name.
        //Otherwise, if it's above 256, set this.name to the first 256 characters of name.
    }

    public void setSerialNumber(String serialNumber) {
        //If serialNumber follows the format A-XXX-XXX-XXX, set this.serialNumber to serialNumber.
    }

    public double getValue() {
        //Return value.
        return 0;
    }

    public String getName() {
        //Return name.
        return "";
    }

    public String getSerialNumber() {
        //Return serialNumber.
        return "";
    }
}

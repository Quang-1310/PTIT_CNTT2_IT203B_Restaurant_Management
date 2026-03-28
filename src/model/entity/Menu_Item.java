package model.entity;

import model.enums.TypeItem;

public class Menu_Item {
    private int itemID;
    private String itemName;
    private int stock;
    private TypeItem type;
    private double price;

    public Menu_Item() {
    }

    public Menu_Item(int itemID, String itemName, int stock, TypeItem type, double price) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.stock = stock;
        this.type = type;
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public TypeItem getType() {
        return type;
    }

    public void setType(TypeItem type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayData(){
        System.out.printf("| %-10s | %-25s | %-10s | %-15s | %-15s |\n",
                this.itemID, this.itemName, this.stock, this.type, this.price);
    }
}

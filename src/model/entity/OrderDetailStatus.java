package model.entity;

import model.enums.StatusOrderDetail;

public class OrderDetailStatus {
    private int id;
    private String itemName;
    private int quantity;
    private StatusOrderDetail status;
    private double price;

    public OrderDetailStatus() {
    }

    public OrderDetailStatus(int id, String itemName, int quantity, StatusOrderDetail status) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
    }

    public OrderDetailStatus(int id, String itemName, int quantity, StatusOrderDetail status, double price) {
        this(id, itemName, quantity, status);
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StatusOrderDetail getStatus() {
        return status;
    }

    public void setStatus(StatusOrderDetail status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void displayData() {
        System.out.printf("| %-10s | %-25s | %-10d | %-15s |\n", id, itemName, quantity, status);
    }
}

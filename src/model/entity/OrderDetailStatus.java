package model.entity;

import model.enums.StatusOrderDetail;

public class OrderDetailStatus {
    private String itemName;
    private int quantity;
    private StatusOrderDetail status;

    public OrderDetailStatus() {
    }

    public OrderDetailStatus(String itemName, int quantity, StatusOrderDetail status) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
    }

    public void displayData() {
        System.out.printf("| %-25s | %-10d | %-15s |\n", itemName, quantity, status);
    }
}

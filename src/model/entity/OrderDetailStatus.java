package model.entity;

import model.enums.StatusOrderDetail;

public class OrderDetailStatus {
    private int id;
    private String itemName;
    private int quantity;
    private StatusOrderDetail status;

    public OrderDetailStatus() {
    }

    public OrderDetailStatus(int id, String itemName, int quantity, StatusOrderDetail status) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
    }

    public void displayData() {
        System.out.printf("| %-10s | %-25s | %-10d | %-15s |\n", id, itemName, quantity, status);
    }
}

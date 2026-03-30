package model.entity;

import model.enums.StatusOrderDetail;

public class ChefTask {
    private int orderDetailId;
    private String itemName;
    private int quantity;
    private int tableId;
    StatusOrderDetail status;
    private String createdAt;

    public ChefTask() {
    }

    public ChefTask(int orderDetailId, String itemName, int quantity, int tableId, StatusOrderDetail status, String createdAt) {
        this.orderDetailId = orderDetailId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.tableId = tableId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public StatusOrderDetail getStatus() {
        return status;
    }

    public void setStatus(StatusOrderDetail status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void displayChefTask() {
        System.out.printf("| %-5d | %-25s | %-8d | %-5d | %-20s | %-15s |\n",
                orderDetailId, itemName, quantity, tableId, createdAt, status);
    }
}

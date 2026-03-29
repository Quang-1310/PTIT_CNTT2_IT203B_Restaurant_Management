package model.entity;

import model.enums.StatusOrder;

import java.util.Date;

public class Order {
    private int orderID;
    private int userId;
    private int tableId;
    private double totalAmount;
    private Date date;
    private StatusOrder status;

    public Order() {
    }

    public Order(int orderID, int userId, int tableId, double totalAmount, Date date, StatusOrder status) {
        this.orderID = orderID;
        this.userId = userId;
        this.tableId = tableId;
        this.totalAmount = totalAmount;
        this.date = date;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }
}

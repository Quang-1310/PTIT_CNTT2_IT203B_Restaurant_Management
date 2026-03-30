package model.entity;

import model.enums.StatusOrderDetail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order_Detail {
    private int orderDetailId;
    private int orderId;
    private int itemId;
    private int quantity;
    private double priceAtOrder;
    private LocalDateTime createdAt;
    private StatusOrderDetail statusItem;

    public Order_Detail() {
    }

    public Order_Detail(int orderDetailId, int orderId, int itemId, int quantity, double priceAtOrder, LocalDateTime createdAt, StatusOrderDetail statusItem) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
        this.createdAt = createdAt;
        this.statusItem = statusItem;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(double priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public StatusOrderDetail getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(StatusOrderDetail statusItem) {
        this.statusItem = statusItem;
    }

    public void displayData(){
        System.out.printf("| %-8d | %-8d | %-8d | %-8d | %-12.2f | %-18s | %-12s |\n",
                this.orderDetailId,
                this.orderId,
                this.itemId,
                this.quantity,
                this.priceAtOrder,
                (this.createdAt != null ? getFormattedTime() : "N/A"),
                this.statusItem);
    }

    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM");
        return createdAt.format(formatter);
    }

}

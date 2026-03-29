package model.entity;

import model.enums.StatusOrderDetail;

public class Order_Detail {
    private int orderDetailId;
    private int orderId;
    private int itemId;
    private int quantity;
    private double priceAtOrder;
    private StatusOrderDetail statusItem;

    public Order_Detail() {
    }

    public Order_Detail(int orderDetailId, int orderId, int itemId, int quantity, double priceAtOrder, StatusOrderDetail statusItem) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
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

    public StatusOrderDetail getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(StatusOrderDetail statusItem) {
        this.statusItem = statusItem;
    }

    public void displayData(){
        System.out.printf("| %-8s | %-8s | %-8s | %-8s | %-15s | | %-15s |\n",
                this.orderDetailId, this.orderId, this.itemId, this.quantity, this.priceAtOrder, this.statusItem);
    }

}

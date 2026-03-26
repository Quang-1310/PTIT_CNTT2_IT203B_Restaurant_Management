package model.entity;

import model.enums.StatusOrderDetail;

public class Order_Detail {
    private int orderDetailId;
    private int orderId;
    private int itemId;
    private int quantity;
    private double priceAtOrder;
    private StatusOrderDetail statusItem;
}

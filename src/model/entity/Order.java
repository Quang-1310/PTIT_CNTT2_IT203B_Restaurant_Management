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
}

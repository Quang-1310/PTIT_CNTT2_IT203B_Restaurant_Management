package business.dao;

import model.entity.Order_Detail;

import java.util.List;

public interface IOrderDao {
    int createOrder(int userId, int tableId);

    List<Order_Detail> getInvoiceDetails(int orderId);
}

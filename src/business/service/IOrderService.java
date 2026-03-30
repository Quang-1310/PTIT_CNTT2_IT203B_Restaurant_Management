package business.service;

import model.entity.Order;
import model.entity.OrderDetailStatus;
import model.entity.Order_Detail;

import java.sql.SQLException;
import java.util.List;

public interface IOrderService {
    boolean placeOrder(int userId , List<Order_Detail> items) throws SQLException;

    List<Order_Detail> getOrderDetailById(int orderId);

    List<OrderDetailStatus> getTrackingDetails(int orderId);

    boolean cancelItem(int id);

    int findActiveOrderIdByUserId(int userId);

    List<Order> findActiveOrdersByUserId(int userId);
}

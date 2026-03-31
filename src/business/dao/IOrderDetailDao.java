package business.dao;

import model.entity.OrderDetailStatus;
import model.entity.Order_Detail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDetailDao {
    List<Order_Detail> getAllOrderDetail();

    void insertOrderDetails(int orderId, List<Order_Detail> details);

    List<Order_Detail> getDetailsByOrderId(int orderId);

    List<OrderDetailStatus> getTrackingDetails(int orderId);

    boolean cancelItem(int id);

    List<OrderDetailStatus> getGroupedItemsByOrder(int orderId);
}

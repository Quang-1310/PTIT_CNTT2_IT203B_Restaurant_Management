package business.service;

import business.dao.MenuItemImpl;
import business.dao.OrderDetailImpl;
import business.dao.OrderImpl;
import model.entity.Order;
import model.entity.OrderDetailStatus;
import model.entity.Order_Detail;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService{
    OrderImpl orderDao = new OrderImpl();
    OrderDetailImpl orderDetailDao = new OrderDetailImpl();
    private MenuItemImpl menuItemDao = new MenuItemImpl();
    @Override
    public boolean placeOrder(int orderId, List<Order_Detail> items) throws SQLException {
        if(items.isEmpty()){
            System.out.println("Danh sách món trống");
            return false;
        }

        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            conn.setAutoCommit(false);

            for (Order_Detail detail : items) {
                boolean stockUpdated = menuItemDao.updateStock(detail.getItemId(), detail.getQuantity());
                if (!stockUpdated) {
                    conn.rollback();
                    return false;
                }
            }

            orderDetailDao.insertOrderDetails(orderId, items);

            conn.commit();
            return true;
        } catch (Exception e) {
            if (conn != null){
                conn.rollback();
            }
            return false;
        }
    }

    @Override
    public List<Order_Detail> getOrderDetailById(int orderId) {
        return orderDetailDao.getDetailsByOrderId(orderId);
    }

    @Override
    public List<OrderDetailStatus> getTrackingDetails(int orderId) {
        return orderDetailDao.getTrackingDetails(orderId);
    }

    @Override
    public boolean cancelItem(int id) {
        return orderDetailDao.cancelItem(id);
    }

    @Override
    public int findActiveOrderIdByUserId(int userId) {
        return orderDao.findActiveOrderIdByUserId(userId);
    }

    @Override
    public List<Order> findActiveOrdersByUserId(int userId) {
        return orderDao.findActiveOrdersByUserId(userId);
    }

}

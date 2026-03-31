package business.dao;

import model.entity.Order;
import model.entity.Order_Detail;
import model.enums.StatusOrderDetail;
import util.DBConnection;
import validate.Validate;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderImpl implements IOrderDao{
    public int createOrder(int userId, int tableId) {
        String sql = "INSERT INTO Orders(user_id, table_id, date, status) VALUES (?, ?, NOW(), 'PENDING')";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, tableId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Lỗi tạo Order: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Order_Detail> getInvoiceDetails(int orderId) {
        List<Order_Detail> list = new ArrayList<>();
        String sql = """
        SELECT od.*, mi.item_name 
        FROM Order_Details od
        JOIN Menu_items mi ON od.item_id = mi.item_id
        WHERE od.order_id = ?
    """;
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order_Detail detail = new Order_Detail();
                detail.setOrderDetailId(rs.getInt("order_detail_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setPriceAtOrder(rs.getDouble("price_at_order"));

                list.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int findActiveOrderIdByUserId(int userId) {
        String sql = "SELECT order_id FROM Orders WHERE user_id = ? AND status = 'PENDING' LIMIT 1";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Order> findActiveOrdersByUserId(int userId) {
        List<Order> activeOrders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE user_id = ? AND status = 'PENDING'";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("order_id"));
                order.setTableId(rs.getInt("table_id"));
                activeOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeOrders;
    }

    public boolean completePayment(int userId, List<Integer> orderIds) {
        String sqlUpdateOrders = "UPDATE Orders SET status = 'PAID' WHERE order_id = ?";
        String sqlUpdateTables = "UPDATE Tables SET status = 'FREE' WHERE table_id = (SELECT table_id FROM Orders WHERE order_id = ?)";

        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psOrder = conn.prepareStatement(sqlUpdateOrders);
                 PreparedStatement psTable = conn.prepareStatement(sqlUpdateTables)) {

                for (int orderId : orderIds) {
                    psOrder.setInt(1, orderId);
                    psOrder.addBatch();

                    psTable.setInt(1, orderId);
                    psTable.addBatch();
                }

                psOrder.executeBatch();
                psTable.executeBatch();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(Validate.ANSI_RED + "Lỗi" + Validate.ANSI_RESET);
            }
        }
        return false;
    }

}

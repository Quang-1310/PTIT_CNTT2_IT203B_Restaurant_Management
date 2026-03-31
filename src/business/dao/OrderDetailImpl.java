package business.dao;

import model.entity.OrderDetailStatus;
import model.entity.Order_Detail;
import model.enums.StatusOrderDetail;
import util.DBConnection;
import validate.Validate;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailImpl implements IOrderDetailDao{
    @Override
    public List<Order_Detail> getAllOrderDetail() {
        List<Order_Detail> orderDetails = new ArrayList<>();

        String sqlGetDetail = """
                SELECT * FROM Order_Details
                """;

        try (Connection conn = DBConnection.openConnection();
             Statement ps = conn.createStatement()) {

            ResultSet rs = ps.executeQuery(sqlGetDetail);
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime ldt = (ts != null) ? ts.toLocalDateTime() : null;

                Order_Detail orderDetail = new Order_Detail(
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_at_order"),
                        ldt,
                        StatusOrderDetail.valueOf(rs.getString("item_status"))
                );
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi");
        }
        return orderDetails;
    }

    @Override
    public void insertOrderDetails(int orderId, List<Order_Detail> details) {
        String sql = "INSERT INTO Order_Details (order_id, item_id, quantity, price_at_order, item_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Order_Detail item : details) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getItemId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPriceAtOrder());
                ps.setString(5, item.getStatusItem().name());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Lỗi");
        }
    }

    @Override
    public List<Order_Detail> getDetailsByOrderId(int orderId) {
        List<Order_Detail> orderDetails = new ArrayList<>();

        String sqlGetDetail = """
                SELECT * FROM Order_Details WHERE order_id = ?
                """;

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sqlGetDetail)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                LocalDateTime ldt = (ts != null) ? ts.toLocalDateTime() : null;
                Order_Detail orderDetail = new Order_Detail(
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_at_order"),
                        ldt,
                        StatusOrderDetail.valueOf(rs.getString("item_status"))
                );
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }


    @Override
    public List<OrderDetailStatus> getTrackingDetails(int orderId) {
        List<OrderDetailStatus> list = new ArrayList<>();
        String sql = """
            SELECT od.order_detail_id, mi.item_name, od.quantity, od.item_status 
            FROM order_details od
            JOIN menu_items mi ON od.item_id = mi.item_id
            WHERE od.order_id = ?
        """;

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new OrderDetailStatus(
                        rs.getInt("order_detail_id"),
                        rs.getString("item_name"),
                        rs.getInt("quantity"),
                        StatusOrderDetail.valueOf(rs.getString("item_status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean cancelItem(int orderDetailId) {
        String sqlGetInfo = "SELECT item_id, quantity FROM Order_Details WHERE order_detail_id = ? AND item_status = 'PENDING'";
        String sqlDelete = "DELETE FROM Order_Details WHERE order_detail_id = ? AND item_status = 'PENDING'";
        String sqlUpdateStock = "UPDATE Menu_Items SET stock = stock + ? WHERE item_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            conn.setAutoCommit(false);

            int itemId = -1;
            int quantity = 0;

            try (PreparedStatement psGet = conn.prepareStatement(sqlGetInfo)) {
                psGet.setInt(1, orderDetailId);
                ResultSet rs = psGet.executeQuery();
                if (rs.next()) {
                    itemId = rs.getInt("item_id");
                    quantity = rs.getInt("quantity");
                } else {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement psDel = conn.prepareStatement(sqlDelete)) {
                psDel.setInt(1, orderDetailId);
                int rows = psDel.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock)) {
                psStock.setInt(1, quantity);
                psStock.setInt(2, itemId);
                psStock.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Lỗi");
                }
            }
            System.out.println(Validate.ANSI_RED + "Lỗi khi hủy món: " + e.getMessage() + Validate.ANSI_RESET);
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi");
            }
        }
        return false;
    }

    @Override
    public List<OrderDetailStatus> getGroupedItemsByOrder(int orderId) {
        List<OrderDetailStatus> list = new ArrayList<>();
        String sql = """
        SELECT mi.item_name, SUM(od.quantity) as total_qty, od.price_at_order 
        FROM Order_Details od
        JOIN Menu_items mi ON od.item_id = mi.item_id
        WHERE od.order_id = ? AND od.item_status IN ('READY', 'SERVED')
        GROUP BY mi.item_name, od.price_at_order
    """;

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetailStatus item = new OrderDetailStatus();
                item.setItemName(rs.getString("item_name"));
                item.setQuantity(rs.getInt("total_qty"));
                item.setPrice(rs.getDouble("price_at_order"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}

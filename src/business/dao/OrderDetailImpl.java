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
        String sqlDelete = "DELETE FROM Order_Details WHERE order_detail_id = ? AND item_status = 'PENDING'";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDelete)) {

            ps.setInt(1, orderDetailId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi khi hủy món: " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }


}

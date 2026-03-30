package business.dao;

import model.entity.ChefTask;
import model.enums.StatusOrderDetail;
import util.DBConnection;
import validate.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChefImpl implements IChefDao{
    @Override
    public List<ChefTask> getPendingTasks() {
        List<ChefTask> tasks = new ArrayList<>();
        String sql = "SELECT od.order_detail_id, mi.item_name, od.quantity, t.table_id, od.created_at, od.item_status " +
                "FROM Order_Details od " +
                "JOIN Menu_items mi ON od.item_id = mi.item_id " +
                "JOIN Orders o ON od.order_id = o.order_id " +
                "JOIN Tables t ON o.table_id = t.table_id " +
                "WHERE od.item_status = 'PENDING' " +
                "ORDER BY od.created_at ASC";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChefTask task = new ChefTask();
                task.setOrderDetailId(rs.getInt("order_detail_id"));
                task.setItemName(rs.getString("item_name"));
                task.setQuantity(rs.getInt("quantity"));
                task.setTableId(rs.getInt("table_id"));
                task.setStatus(StatusOrderDetail.valueOf(rs.getString("item_status")));
                task.setCreatedAt(rs.getString("created_at"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi" + Validate.ANSI_RESET);
        }
        return tasks;
    }

    @Override
    public void updateNextStatus(int orderDetailId) {
        String sqlGetStatus = "SELECT item_status FROM Order_Details WHERE order_detail_id = ?";
        String sqlUpdate = "UPDATE Order_Details SET item_status = ? WHERE order_detail_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement psGet = conn.prepareStatement(sqlGetStatus)) {

            psGet.setInt(1, orderDetailId);
            ResultSet rs = psGet.executeQuery();

            if (rs.next()) {
                String currentStatus = rs.getString("item_status");
                String nextStatus = null;

                switch (currentStatus) {
                    case "PENDING":
                        nextStatus = "COOKING";
                        break;
                    case "COOKING":
                        nextStatus = "READY";
                        break;
                    case "READY":
                        nextStatus = "SERVED";
                        break;
                    case "SERVED":
                        System.out.println(Validate.ANSI_YELLOW + "Món này đã hoàn tất phục vụ, không thể chuyển tiếp!" + Validate.ANSI_RESET);
                        return;
                }

                if (nextStatus != null) {
                    try (PreparedStatement psUp = conn.prepareStatement(sqlUpdate)) {
                        psUp.setString(1, nextStatus);
                        psUp.setInt(2, orderDetailId);
                        int count = psUp.executeUpdate();
                        if (count > 0) {
                            System.out.println(Validate.ANSI_GREEN + "Đã chuyển trạng thái sang: " + nextStatus + Validate.ANSI_RESET);
                        }
                    }
                }
            } else {
                System.out.println(Validate.ANSI_RED + "Không tìm thấy mã món ăn này!" + Validate.ANSI_RESET);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi database: " + e.getMessage());
        }
    }
}

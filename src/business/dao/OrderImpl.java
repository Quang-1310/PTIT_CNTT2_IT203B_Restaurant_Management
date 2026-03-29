package business.dao;

import util.DBConnection;

import java.sql.*;

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
}

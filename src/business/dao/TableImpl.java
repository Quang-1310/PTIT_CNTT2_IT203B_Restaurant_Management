package business.dao;

import model.entity.Table;
import model.enums.StatusTable;
import util.DBConnection;
import validate.Validate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableImpl implements ITableDao{
    @Override
    public List<Table> getAllTable() {
        List<Table> tableList = new ArrayList<>();
        String sql = "SELECT * FROM tables";

        try (Connection conn = DBConnection.openConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int seatId = rs.getInt("table_id");
                int seatCapacity = rs.getInt("seat_capacity");
                StatusTable status = StatusTable.valueOf(rs.getString("status"));

                Table newTable = new Table(seatId, seatCapacity, status);
                tableList.add(newTable);
            }
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi: " + e.getMessage() + Validate.ANSI_RESET);
        }
        return tableList;
    }

    @Override
    public boolean insertTable(Table newTable) {
        String sql = "INSERT INTO tables(seat_capacity, status) VALUES (?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newTable.getSeatCapacity());
            ps.setString(2, newTable.getStatus().name());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi: " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public boolean updateTable(int id, int newSeatCapacity, StatusTable newStatus) {
        String sql = "UPDATE tables SET seat_capacity = ?, status = ? WHERE table_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newSeatCapacity);
            ps.setString(2, newStatus.name());
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi: " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public boolean deleteTable(int id) {
        String sql = "DELETE FROM tables WHERE table_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi: " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public Table findTableById(int id) {
        String sql = "SELECT * FROM tables WHERE table_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Table(
                        rs.getInt("table_id"),
                        rs.getInt("seat_capacity"),
                        StatusTable.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi: " + e.getMessage() + Validate.ANSI_RESET);
        }
        return null;
    }

    @Override
    public List<Table> findTableByStatus(StatusTable status) {
        List<Table> tableList = new ArrayList<>();
        String sql = """
                select * from tables where status = ?
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, status.name());

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("table_id");
                int seatCapacity = rs.getInt("seat_capacity");
                StatusTable getStatus = StatusTable.valueOf(rs.getString("status"));

                Table table = new Table(id, seatCapacity, getStatus);
                tableList.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tableList;
    }
}

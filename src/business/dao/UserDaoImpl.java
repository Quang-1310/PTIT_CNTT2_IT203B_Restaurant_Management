package business.dao;

import model.entity.User;
import model.enums.UserRole;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements IUserDao {
    @Override
    public User login(String email, String password){
        String sqlSelectUser = """
                select * from Users where email = ? and password = ? and is_active = true
                """;
        try(Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelectUser)){

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new User(rs.getInt("user_id"), rs.getString("user_name"),rs.getString("email"),rs.getString("password"),rs.getString("phone"), UserRole.valueOf(rs.getString("user_role")),rs.getBoolean("is_active"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean register(User user){
        String sqlInsert = """
                insert into Users(user_name, email, password, phone, user_role) values(?, ?, ?, ?, 'Customer')
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlInsert)){

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());

            int count = ps.executeUpdate();
            if(count > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        UserRole.valueOf(rs.getString("user_role")),
                        rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

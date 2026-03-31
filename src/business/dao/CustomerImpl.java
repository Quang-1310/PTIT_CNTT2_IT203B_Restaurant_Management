package business.dao;

import model.entity.User;
import model.enums.UserRole;
import util.DBConnection;
import validate.Validate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerImpl implements ICustomerDao{
    @Override
    public List<User> getAllCustomer() {
        List<User> userList = new ArrayList<>();
        String sqlGetAllCustomer = """
                select * from Users where user_role = 'CUSTOMER'
                """;

        try(Connection conn = DBConnection.openConnection();
            Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sqlGetAllCustomer);
            while(rs.next()){
                int id = rs.getInt("user_id");
                String nameUser = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                UserRole role = UserRole.valueOf(rs.getString("user_role"));
                boolean isActive = rs.getBoolean("is_active");

                User user = new User();
                user.setUserID(id);
                user.setUserName(nameUser);
                user.setEmail(email);
                user.setPassword(password);
                user.setPhone(phone);
                user.setRole(role);
                user.setActive(isActive);

                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi" + Validate.ANSI_YELLOW);
        }
        return userList;
    }

    @Override
    public List<User> findCustomerByName(String name) {
        List<User> userList = new ArrayList<>();
        String sqlSearchCustomer = """
                select * from Users where user_name like ?
                """;
        try(Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSearchCustomer)){

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("user_id");
                String nameUser = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                UserRole role = UserRole.valueOf(rs.getString("user_role"));
                boolean isActive = rs.getBoolean("is_active");

                User user = new User();
                user.setUserID(id);
                user.setUserName(nameUser);
                user.setEmail(email);
                user.setPassword(password);
                user.setPhone(phone);
                user.setRole(role);
                user.setActive(isActive);

                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi" + Validate.ANSI_YELLOW);
        }
        return userList;
    }

    @Override
    public boolean banAccount(int id) {
        String sql = """
                update Users set is_active = false where user_id = ? and user_role = 'CUSTOMER'
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            if(ps.executeUpdate() > 0){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi Ban tài khoản" + Validate.ANSI_RESET);
        }
        return false;
    }
}

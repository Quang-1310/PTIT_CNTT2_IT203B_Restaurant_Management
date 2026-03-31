package business.dao;

import model.entity.User;
import model.entity.User;
import model.enums.TypeItem;
import model.enums.UserRole;
import util.DBConnection;
import validate.Validate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeImpl implements IEmployeeDao{
    @Override
    public List<User> getAllEmployee() {
        List<User> userList = new ArrayList<>();
        String sqlSelect = """
                select * from Users where user_role = 'CHEF'
                """;

        try(Connection conn = DBConnection.openConnection();
            Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(sqlSelect);
            while(rs.next()){
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                UserRole role = UserRole.valueOf(rs.getString("user_role"));
                boolean isActive = rs.getBoolean("is_active");

                User chef = new User();
                chef.setUserID(id);
                chef.setUserName(name);
                chef.setEmail(email);
                chef.setPassword(password);
                chef.setPhone(phone);
                chef.setRole(role);
                chef.setActive(isActive);


                userList.add(chef);
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi" + Validate.ANSI_RESET);
        }
        return userList;
    }

    @Override
    public boolean insertEmployee(User employee) {
        String sqlUpdate = """
                insert into Users(user_name, email, password, phone, user_role) values(?,?,?,?,?)
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlUpdate)){

            ps.setString(1, employee.getUserName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPassword());
            ps.setString(4, employee.getPhone());
            ps.setString(5, employee.getRole().name());


            int count = ps.executeUpdate();
            if(count > 0){
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi" + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public boolean updateEmployee(int id, String newName, String newEmail, String newPassword, String newPhone) {
        String sqlUpdate = """
                update Users set user_name = ?, email = ?, password = ?, phone = ? where user_id = ?
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlUpdate)){
            ps.setString(1, newName);
            ps.setString(2, newEmail);
            ps.setString(3, newPassword);
            ps.setString(4, newPhone);
            ps.setInt(5, id);

            int count = ps.executeUpdate();
            if(count > 0){
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Lỗi");
        }

        return false;
    }

    @Override
    public boolean deleteEmployee(int id) {
        String sqlDelete = """
                delete from Users where user_id = ?
                """;

        try(Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDelete)){
            ps.setInt(1, id);

            int count = ps.executeUpdate();
            if(count > 0){
                return true;
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public List<User> findEmployeeByName(String name) {
        List<User> userList = new ArrayList<>();
        String sqlSelect = """
                select * from Users where user_name LIKE ? AND user_role = 'CHEF'
                """;

        try(Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelect)){
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int getId = rs.getInt("user_id");
                String getName = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                UserRole role = UserRole.valueOf(rs.getString("user_role"));
                boolean isActive = rs.getBoolean("is_active");

                User chef = new User();
                chef.setUserID(getId);
                chef.setUserName(getName);
                chef.setEmail(email);
                chef.setPassword(password);
                chef.setPhone(phone);
                chef.setRole(role);
                chef.setActive(isActive);

                userList.add(chef);
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi " + e.getMessage() + Validate.ANSI_RESET);
        }
        return userList;
    }
}

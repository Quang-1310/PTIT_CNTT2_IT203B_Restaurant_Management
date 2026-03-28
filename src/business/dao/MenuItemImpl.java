package business.dao;

import exception.RestaurantException;
import model.entity.Menu_Item;
import model.enums.TypeItem;
import util.DBConnection;
import validate.Validate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemImpl implements IMenuItemDao{
    @Override
    public List<Menu_Item> getAllItem() {
        List<Menu_Item> menuItemList = new ArrayList<>();
        String sqlSelecte = """
                select * from Menu_items
                """;

        try(Connection conn = DBConnection.openConnection();
            Statement stmt = conn.createStatement()) {

            ResultSet rs =  stmt.executeQuery(sqlSelecte);

            while(rs.next()){
                int item_id = rs.getInt("item_id");
                String item_name = rs.getString("item_name");
                int stock = rs.getInt("stock");
                TypeItem type = TypeItem.valueOf(rs.getString("type"));
                double price = rs.getDouble("price");

                Menu_Item menuItem = new Menu_Item(item_id, item_name, stock, type, price);
                menuItemList.add(menuItem);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());

        }
        return menuItemList;
    }

    @Override
    public boolean insertMenuItem(Menu_Item menuItem) {
        String sqlInsertItem = """
                insert into Menu_items(item_name, stock, type, price) values(?,?,?,?)
                """;

        try(Connection conn = DBConnection.openConnection();
            PreparedStatement ps = conn.prepareStatement(sqlInsertItem)){

            ps.setString(1, menuItem.getItemName());
            ps.setInt(2, menuItem.getStock());
            ps.setString(3, menuItem.getType().name());
            ps.setDouble(4, menuItem.getPrice());

            int conunt = ps.executeUpdate();
            if(conunt > 0){
                return true;
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public boolean updateMenuItem(int id, String newName, int newStock, TypeItem newType, double newPrice) {
        String sqlUpdateItem = """
                update Menu_items set item_name = ?, stock = ?, type = ?, price = ? where item_id = ?
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlUpdateItem)){

            ps.setString(1, newName);
            ps.setInt(2, newStock);
            ps.setString(3, newType.name());
            ps.setDouble(4, newPrice);
            ps.setInt(5, id);

            int count = ps.executeUpdate();
            if(count > 0){
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi " + e.getMessage() + Validate.ANSI_RESET);
        }
        return false;
    }

    @Override
    public boolean deleteMenuItem(int id) {
        String sqlDeteleItem = """
                delete from Menu_items where item_id = ?
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlDeteleItem)){
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
    public Menu_Item findItemById(int id) {
        String sqlSelect = """
                select * from Menu_items where item_id = ?
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlSelect)){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int idItem = rs.getInt("item_id");
                String nameItem = rs.getString("item_name");
                int stock = rs.getInt("stock");
                TypeItem type = TypeItem.valueOf(rs.getString("type"));
                double price = rs.getDouble("price");

                Menu_Item menuItem = new Menu_Item(idItem, nameItem, stock, type, price);
                return menuItem;
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi " + e.getMessage() + Validate.ANSI_RESET);
        }
        return null;
    }

    @Override
    public List<Menu_Item> findItemByName(String name) {
        List<Menu_Item> menuItemList = new ArrayList<>();
        String sqlSelect = """
                select * from Menu_items where item_name like ?
                """;

        try(Connection conn = DBConnection.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlSelect)){
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idItem = rs.getInt("item_id");
                String nameItem = rs.getString("item_name");
                int stock = rs.getInt("stock");
                TypeItem type = TypeItem.valueOf(rs.getString("type"));
                double price = rs.getDouble("price");

                Menu_Item menuItem = new Menu_Item(idItem, nameItem, stock, type, price);
                menuItemList.add(menuItem);
            }

        } catch (SQLException e) {
            System.out.println(Validate.ANSI_RED + "Lỗi " + e.getMessage() + Validate.ANSI_RESET);
        }
        return menuItemList;
    }
}

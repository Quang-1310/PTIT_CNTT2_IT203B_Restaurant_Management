package business.dao;

import model.entity.Menu_Item;
import model.enums.TypeItem;

import java.util.List;

public interface IMenuItemDao {
    List<Menu_Item> getAllItem();
    boolean insertMenuItem(Menu_Item menuItem);
    boolean updateMenuItem(int id, String newName, int newStock, TypeItem newType, double newPrice);
    boolean deleteMenuItem(int id);
    Menu_Item findItemById(int id);
    List<Menu_Item> findItemByName(String name);
}

package business.service;

import model.entity.Menu_Item;
import model.enums.TypeItem;

import java.util.List;

public interface IMenuItemService {
    List<Menu_Item> getAllMenuItem();
    boolean insertMenuItem(Menu_Item menuItem);
    boolean updateMenuItem(int id, String newName, int newStock, TypeItem newType, double newPrice);
    boolean deleteItem(int id);
    Menu_Item findItemById(int id);
    List<Menu_Item> findItemByName(String name);
}

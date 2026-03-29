package business.service;

import business.dao.MenuItemImpl;
import model.entity.Menu_Item;
import model.enums.TypeItem;

import java.util.List;

public class MenuItemServiceImpl implements IMenuItemService {
    private MenuItemImpl menuItemDao = new MenuItemImpl();

    @Override
    public List<Menu_Item> getAllMenuItem(){
        return menuItemDao.getAllItem();
    }

    @Override
    public boolean insertMenuItem(Menu_Item menuItem) {
        return menuItemDao.insertMenuItem(menuItem);
    }

    @Override
    public boolean updateMenuItem(int id, String newName, int newStock, TypeItem newType, double newPrice) {
        return menuItemDao.updateMenuItem(id, newName, newStock, newType, newPrice);
    }

    @Override
    public boolean deleteItem(int id) {
        return menuItemDao.deleteMenuItem(id);
    }

    @Override
    public Menu_Item findItemById(int id) {
        return menuItemDao.findItemById(id);
    }

    @Override
    public List<Menu_Item> findItemByName(String name) {
        return menuItemDao.findItemByName(name);
    }

    @Override
    public int getStockById(int itemId) {
        return menuItemDao.getStockById(itemId);
    }
}

package business.dao;

import model.entity.Menu_Item;
import model.entity.User;
import model.enums.TypeItem;

import java.util.List;

public interface IEmployeeDao {
    List<User> getAllEmployee();
    boolean insertEmployee(User employee);
    boolean updateEmployee(int id, String newName, String newEmail, String newPassword, String newPhone);
    boolean deleteEmployee(int id);
    List<User> findEmployeeByName(String name);
}

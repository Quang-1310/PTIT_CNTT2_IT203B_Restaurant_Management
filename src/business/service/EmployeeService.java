package business.service;

import business.dao.EmployeeImpl;
import business.dao.IEmployeeDao;
import model.entity.User;

import java.util.List;

public class EmployeeService implements IEmployeeDao {
    EmployeeImpl employeeDao = new EmployeeImpl();

    @Override
    public List<User> getAllEmployee() {
        return employeeDao.getAllEmployee();
    }

    @Override
    public boolean insertEmployee(User employee) {
        return employeeDao.insertEmployee(employee);
    }

    @Override
    public boolean updateEmployee(int id, String newName, String newEmail, String newPassword, String newPhone) {
        return employeeDao.updateEmployee(id, newName, newEmail, newPassword, newPhone);
    }

    @Override
    public boolean deleteEmployee(int id) {
        return employeeDao.deleteEmployee(id);
    }

    @Override
    public List<User> findEmployeeByName(String name) {
        return employeeDao.findEmployeeByName(name);
    }

}

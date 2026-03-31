package business.service;

import business.dao.CustomerImpl;
import business.dao.ICustomerDao;
import model.entity.User;

import java.util.List;

public class CustomerService implements ICustomerDao {
    CustomerImpl customerDao = new CustomerImpl();

    @Override
    public List<User> getAllCustomer() {
        return customerDao.getAllCustomer();
    }

    @Override
    public List<User> findCustomerByName(String name) {
        return customerDao.findCustomerByName(name);
    }

    @Override
    public boolean banAccount(int id) {
        return customerDao.banAccount(id);
    }
}

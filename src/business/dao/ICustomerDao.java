package business.dao;

import model.entity.User;

import java.util.List;

public interface ICustomerDao {
    List<User> getAllCustomer();
    List<User> findCustomerByName(String name);
}

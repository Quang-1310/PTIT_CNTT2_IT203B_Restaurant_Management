package business.service;

import model.entity.User;

import java.util.List;

public interface ICustomerDao {
    List<User> getAllCustomer();
    List<User> findCustomerByName(String name);

    boolean banAccount(int id);
}

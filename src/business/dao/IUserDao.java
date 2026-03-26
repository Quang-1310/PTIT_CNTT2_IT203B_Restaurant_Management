package business.dao;

import model.entity.User;

public interface IUserDao {
    User login(String email, String password);
    boolean register(User user);
}

package business.service;

import business.dao.UserDaoImpl;
import model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import util.PasswordHasher;

public class UserService {
    private UserDaoImpl userDao = new UserDaoImpl();

    public User handleLogin(String email, String plainPassword) {
        User user = userDao.login(email, plainPassword);

        if (user != null) {
            if (user.isActive()) {
                return user;
            }
        }
        return null;
    }

    public boolean handleRegister(User user) {
        if (userDao.findByEmail(user.getEmail()) != null) {
            return false;
        }

        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        return userDao.register(user);
    }

    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }
}

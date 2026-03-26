package business.service;

import business.dao.UserDaoImpl;
import model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import util.PasswordHasher;

public class UserService {
    private UserDaoImpl userDao = new UserDaoImpl();

    public User handleLogin(String email, String plainPassword) {
        User user = userDao.findByEmail(email);

        if (user != null && PasswordHasher.checkPassword(plainPassword, user.getPassword())) {
            if (user.isActive()) {
                return user;
            }
        }
        return null;
    }

    public boolean handleRegister(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        return userDao.register(user);
    }
}

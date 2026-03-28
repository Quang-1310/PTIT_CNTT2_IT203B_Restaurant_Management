package presentation;

import business.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        RestaurantApplication app = new RestaurantApplication();
        app.start();
    }
}
package controller;

import controller.UserManager;
import model.User;

public class UserController {

    public static User login(String username, String password) {
        return UserManager.login(username, password);
    }

    public static boolean register(String username, String password) {
        return UserManager.register(username, password);
    }
}

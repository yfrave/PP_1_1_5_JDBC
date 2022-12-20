package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Roma", "Chernikov", (byte) 20);
        userService.saveUser("Sanya", "Chernikov1", (byte) 10);
        userService.saveUser("Ro5", "Sulov", (byte) 90);
        userService.saveUser("Tom", "Bazuka", (byte) 66);

        userService.getAllUsers();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.close();
    }
}

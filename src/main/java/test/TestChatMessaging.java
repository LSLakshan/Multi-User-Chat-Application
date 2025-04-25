package test;

import common.dao.UserDAO;
import common.dao.UserDAOImpl;
import common.model.User;

public class TestUserDAO {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAOImpl();

        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("1234");
        user.setNickname("Tester");
        user.setRole(User.Role.USER);
        

        userDAO.saveUser(user);

        User fetched = userDAO.getUserByEmail("test@example.com");
        System.out.println("Fetched User: " + fetched.getUsername());
    }
}

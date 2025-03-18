package cat.itacademy.s05.t02.Service;

import cat.itacademy.s05.t02.Models.User;

public interface UserService {
    User registerUser(User user);
    boolean loginUser(String username, String password);
    User findByUsername(String username);
    void authenticateUser(String username, String password) throws Exception;
    void addCurrency(Long userId, int currency);
    void subtractCurrency(Long userId, int amount);
    User findById(Long id);
}
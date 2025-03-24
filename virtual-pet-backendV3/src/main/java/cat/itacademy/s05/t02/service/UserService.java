package cat.itacademy.s05.t02.service;

import cat.itacademy.s05.t02.models.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    boolean loginUser(String username, String password);
    User findByUsername(String username);
    void authenticateUser(String username, String password) throws Exception;
    void addCurrency(Long userId, int currency);
    void subtractCurrency(Long userId, int amount);
    User findById(Long id);
    void incrementWins(Long userId);
    List<User> getUsersRankedByWins();
}
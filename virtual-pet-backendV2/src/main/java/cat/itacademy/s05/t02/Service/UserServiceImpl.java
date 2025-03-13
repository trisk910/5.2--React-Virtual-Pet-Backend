package cat.itacademy.s05.t02.Service;

    import cat.itacademy.s05.t02.Models.User;
    import cat.itacademy.s05.t02.Repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class UserServiceImpl implements UserService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public User registerUser(User user) {
            return userRepository.save(user);
        }

        @Override
        public boolean loginUser(String username, String password) {
            User existingUser = userRepository.findByUsername(username);
            return existingUser != null && existingUser.getPassword().equals(password);
        }

        @Override
        public User findByUsername(String username) {
            return userRepository.findByUsername(username);
        }
    }
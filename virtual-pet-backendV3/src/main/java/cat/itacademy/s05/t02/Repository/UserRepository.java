package cat.itacademy.s05.t02.Repository;

import cat.itacademy.s05.t02.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
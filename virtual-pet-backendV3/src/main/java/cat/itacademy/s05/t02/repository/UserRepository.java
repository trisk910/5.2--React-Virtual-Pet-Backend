package cat.itacademy.s05.t02.repository;

import cat.itacademy.s05.t02.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByOrderByWinsDesc();
}
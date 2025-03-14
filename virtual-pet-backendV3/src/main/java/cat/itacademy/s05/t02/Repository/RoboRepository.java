package cat.itacademy.s05.t02.Repository;


import cat.itacademy.s05.t02.Models.Robo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoboRepository extends JpaRepository<Robo, Long> {
    List<Robo> findByUserId(Long userId);
}

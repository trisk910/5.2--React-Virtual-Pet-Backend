package cat.itacademy.s05.t02.Repository;


import cat.itacademy.s05.t02.Models.Robo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoboRepository extends JpaRepository<Robo, Long> {
}

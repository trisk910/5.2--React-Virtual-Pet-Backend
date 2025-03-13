package cat.itacademy.s05.t02.Repository;

import cat.itacademy.s05.t02.Models.Fight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FightRepository extends JpaRepository<Fight, Long> {
}
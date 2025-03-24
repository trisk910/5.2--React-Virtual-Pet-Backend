package cat.itacademy.s05.t02.repository;

import cat.itacademy.s05.t02.models.Fight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FightRepository extends JpaRepository<Fight, Long> {
}
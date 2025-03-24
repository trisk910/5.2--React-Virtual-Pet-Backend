package cat.itacademy.s05.t02.service;

import cat.itacademy.s05.t02.models.Robo;
import java.util.List;

public interface RoboService {
    Robo buildRobo(Robo robo);
    void destroyRobo(Long id);
    List<Robo> getAllRobos();
    List<Robo> getRobosByUserId(Long userId);
    Robo getRoboById(Long id);
    Robo updateRobo(Robo robo);
    List<Robo> repairAllRobos();
    boolean upgradeRobo(Long roboId);
    int getUpgradeCost(Long roboId);
    void repairRobo(Robo existingRobo);
}
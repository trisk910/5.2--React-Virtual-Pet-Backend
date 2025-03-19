package cat.itacademy.s05.t02.Service;

import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Repository.RoboRepository;
import cat.itacademy.s05.t02.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoboServiceImpl implements RoboService {

    private static final int STAT_INCREMENT_COST = 25;

    @Autowired
    private RoboRepository roboRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Robo buildRobo(Robo robo) {
        return roboRepository.save(robo);
    }

    @Override
    public void destroyRobo(Long id) {
        roboRepository.deleteById(id);
    }

    @Override
    public List<Robo> getAllRobos() {
        return roboRepository.findAll();
    }

    @Override
    public Robo getRoboById(Long id) {
        return roboRepository.findById(id).orElse(null);
    }

    @Override
    public Robo updateRobo(Robo robo) {
        return roboRepository.save(robo);
    }

    @Override
    public List<Robo> getRobosByUserId(Long userId) {
        return roboRepository.findByUserId(userId);
    }

    public List<Robo> repairAllRobos() {
        List<Robo> robos = getAllRobos();
        for (Robo robo : robos) {
            robo.setHealth(robo.getOriginalHealth());
            robo.setAttack(robo.getOriginalAttack());
            robo.setDefense(robo.getOriginalDefense());
            robo.setSpeed(robo.getOriginalSpeed());
            updateRobo(robo);
        }
        return robos;
    }

    public boolean incrementStat(Long roboId, String stat) {
        Robo robo = roboRepository.findById(roboId).orElse(null);
        if (robo == null) {
            return false;
        }

        User user = userRepository.findById(robo.getUserId()).orElse(null);
        if (user == null || user.getCurrency() < STAT_INCREMENT_COST) {
            return false;
        }

        switch (stat.toLowerCase()) {
            case "health":
                robo.setHealth(robo.getHealth() + 1);
                break;
            case "attack":
                robo.setAttack(robo.getAttack() + 1);
                break;
            case "defense":
                robo.setDefense(robo.getDefense() + 1);
                break;
            case "speed":
                robo.setSpeed(robo.getSpeed() + 1);
                break;
        }
        user.setCurrency(user.getCurrency() - STAT_INCREMENT_COST);
        roboRepository.save(robo);
        userRepository.save(user);
        return true;
    }
}
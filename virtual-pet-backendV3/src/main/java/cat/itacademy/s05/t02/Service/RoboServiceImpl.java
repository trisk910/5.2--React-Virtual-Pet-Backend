package cat.itacademy.s05.t02.Service;

import cat.itacademy.s05.t02.Models.Robo;
import cat.itacademy.s05.t02.Models.User;
import cat.itacademy.s05.t02.Repository.RoboRepository;
import cat.itacademy.s05.t02.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RoboServiceImpl implements RoboService {

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

    @Override
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
    @Override
    public boolean upgradeRobo(Long roboId) {
        Robo robo = roboRepository.findById(roboId).orElse(null);
        if (robo == null) {
            return false;
        }

        User user = userRepository.findById(robo.getUserId()).orElse(null);
        if (user == null) {
            return false;
        }

        int incrementCost = (int) (25 * Math.pow(1.04, robo.getLevel() - 1));
        if (user.getCurrency() < incrementCost) {
            return false;
        }

        Random random = new Random();
        int statsToIncrement = random.nextInt(4) + 1;
        for (int i = 0; i < statsToIncrement; i++) {
            int statIndex = random.nextInt(4);
            int percentage = random.nextInt(5) + 2;
            switch (statIndex) {
                case 0:
                    robo.setHealth((int) (robo.getHealth() * (1 + percentage / 100.0)));
                    break;
                case 1:
                    robo.setAttack((int) (robo.getAttack() * (1 + percentage / 100.0)));
                    break;
                case 2:
                    robo.setDefense((int) (robo.getDefense() * (1 + percentage / 100.0)));
                    break;
                case 3:
                    robo.setSpeed((int) (robo.getSpeed() * (1 + percentage / 100.0)));
                    break;
            }
        }
        user.setCurrency(user.getCurrency() - incrementCost);
        robo.setLevel(robo.getLevel() + 1);
        roboRepository.save(robo);
        userRepository.save(user);
        return true;
    }

    @Override
    public int getUpgradeCost(Long roboId) {
        Robo robo = roboRepository.findById(roboId).orElse(null);
        if (robo == null) {
            throw new IllegalArgumentException("Robo not found");
        }
        return (int) (25 * Math.pow(1.04, robo.getLevel() - 1));
    }
}
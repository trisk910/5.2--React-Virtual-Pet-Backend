package cat.itacademy.s05.t02.service;

import cat.itacademy.s05.t02.exceptions.InsuficientCreditsException;
import cat.itacademy.s05.t02.exceptions.RoboNotFoundException;
import cat.itacademy.s05.t02.exceptions.UserNotFoundException;
import cat.itacademy.s05.t02.models.Robo;
import cat.itacademy.s05.t02.models.User;
import cat.itacademy.s05.t02.repository.RoboRepository;
import cat.itacademy.s05.t02.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class RoboServiceImpl implements RoboService {
    private static final Logger logger = Logger.getLogger(RoboServiceImpl.class.getName());
    private static final int REQUIRED_CURRENCY = 100;
    private static final String ROBO_NOT_FOUND = "Robo not found";

    private final RoboRepository roboRepository;

    private final UserRepository userRepository;

    public RoboServiceImpl(RoboRepository roboRepository, UserRepository userRepository) {
        this.roboRepository = roboRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Robo buildRobo(Robo robo) {
        User user = userRepository.findById(robo.getUserId()).orElse(null);
        if (user == null)
            throw new UserNotFoundException("User not found");
        if (user.getCurrency() < REQUIRED_CURRENCY)
            throw new InsuficientCreditsException("Insufficient Credits");
        user.setCurrency(user.getCurrency() - REQUIRED_CURRENCY);
        userRepository.save(user);
        return roboRepository.save(robo);
    }
    @Override
    public void destroyRobo(Long id) {
        Robo robo = roboRepository.findById(id).orElse(null);
        if (robo == null)
            throw new RoboNotFoundException(ROBO_NOT_FOUND);
        else{
            User user = userRepository.findById(robo.getUserId()).orElse(null);
            if (user == null)
                throw new UserNotFoundException("User not found");
            else {
                user.setCurrency(user.getCurrency() + 100);
                userRepository.save(user);
                roboRepository.deleteById(id);
            }
        }
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
                    robo.setOriginalHealth((int) (robo.getOriginalHealth() * (1 + percentage / 100.0)));

                    break;
                case 1:
                    robo.setOriginalAttack((int) (robo.getOriginalAttack() * (1 + percentage / 100.0)));
                    break;
                case 2:
                    robo.setOriginalDefense((int) (robo.getOriginalDefense() * (1 + percentage / 100.0)));
                    break;
                case 3:
                    robo.setOriginalSpeed((int) (robo.getOriginalSpeed() * (1 + percentage / 100.0)));
                    break;
                default:
                    logger.warning("Invalid stat index");
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
            throw new RoboNotFoundException(ROBO_NOT_FOUND);
        }
        return (int) (25 * Math.pow(1.04, robo.getLevel() - 1));
    }

    @Override
    public void repairRobo(Robo existingRobo) {
        Robo robo = roboRepository.findById(existingRobo.getId()).orElse(null);
        if (robo == null) {
            throw new RoboNotFoundException(ROBO_NOT_FOUND);
        }
        robo.setHealth(robo.getOriginalHealth());
        robo.setAttack(robo.getOriginalAttack());
        robo.setDefense(robo.getOriginalDefense());
        robo.setSpeed(robo.getOriginalSpeed());
        roboRepository.save(robo);
    }
}
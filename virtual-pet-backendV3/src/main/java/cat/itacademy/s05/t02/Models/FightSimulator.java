package cat.itacademy.s05.t02.Models;

import cat.itacademy.s05.t02.Models.Enums.CombatPhrases;
import cat.itacademy.s05.t02.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FightSimulator {

    private final Random random = new Random();
    @Autowired
    private UserService userService;

    public List<String> simulateFight(Robo robo1, Robo robo2) {
        List<String> combatLog = new ArrayList<>();

        if (!robo1.canFight() || !robo2.canFight()) {
            combatLog.add("One or both Robos cannot fight due to heavy damage.");
            return combatLog;
        }

        int initialHealthRobo1 = robo1.getHealth();
        int initialHealthRobo2 = robo2.getHealth();
        int currentHealthRobo1 = robo1.getHealth();
        int currentHealthRobo2 = robo2.getHealth();

        Robo firstAttacker = robo1.getSpeed() >= robo2.getSpeed() ? robo1 : robo2;
        Robo secondAttacker = firstAttacker == robo1 ? robo2 : robo1;

        do {
            combatLog.add(performAttack(firstAttacker, secondAttacker));
            if (secondAttacker.getHealth() <= 0) {
                combatLog.add(firstAttacker.getName() + " " + CombatPhrases.WINNER.getPhrase());
                userService.addCurrency(firstAttacker.getUserId(), 100);
                resetHealthAndStats(robo1, initialHealthRobo1, currentHealthRobo1);
                resetHealthAndStats(robo2, initialHealthRobo2, currentHealthRobo2);
                break;
            }
            combatLog.add(performAttack(secondAttacker, firstAttacker));
            if (firstAttacker.getHealth() <= 0) {
                combatLog.add(secondAttacker.getName() + " " + CombatPhrases.WINNER.getPhrase());
                userService.addCurrency(secondAttacker.getUserId(), 100);
                resetHealthAndStats(robo1, initialHealthRobo1, currentHealthRobo1);
                resetHealthAndStats(robo2, initialHealthRobo2, currentHealthRobo2);
            }
        } while (robo1.getHealth() > 0 && robo2.getHealth() > 0);

        return combatLog;
    }

    private String performAttack(Robo attacker, Robo defender) {
        if (random.nextBoolean()) {
            int damage = random.nextInt(attacker.getAttack());
            defender.setHealth(defender.getHealth() - damage);
            String attackLog = String.format(attacker.getName() + " " + CombatPhrases.ATTACK_HIT.getPhrase(), damage);

            if (random.nextDouble() < 0.15) {
                int counterDamage = random.nextInt(defender.getAttack());
                attacker.setHealth(attacker.getHealth() - counterDamage);
                attackLog += " And " + String.format(defender.getName() + " " + CombatPhrases.COUNTER_ATTACK.getPhrase(), counterDamage);
            }
            return attackLog;
        } else {
            return attacker.getName() + " " + CombatPhrases.ATTACK_MISS.getPhrase();
        }
    }

    private void resetHealthAndStats(Robo robo, int initialHealth, int currentHealth) {
        robo.setHealth(currentHealth);
        if (robo.getHealth() < initialHealth) {
            robo.reduceStats(robo.getHealth() == currentHealth);
        }
    }
}
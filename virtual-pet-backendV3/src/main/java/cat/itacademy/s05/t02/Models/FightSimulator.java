package cat.itacademy.s05.t02.Models;

import cat.itacademy.s05.t02.Models.Enums.CombatPhrases;
import cat.itacademy.s05.t02.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FightSimulator {

    private final Random random = new Random();
    private final UserService userService;

    public FightSimulator(UserService userService) {
        this.userService = userService;
    }

    public List<String> simulateFight(Robo robo1, Robo robo2, String ownerUsername1, String ownerUsername2) {
        List<String> combatLog = new ArrayList<>();

        if (!robo1.canFight() || !robo2.canFight()) {
            combatLog.add("One or both Robos cannot fight due to heavy damage.");
            return combatLog;
        }
        int initialHealthRobo1 = robo1.getHealth();
        int initialHealthRobo2 = robo2.getHealth();
        int currentHealthRobo1 = robo1.getHealth();
        int currentHealthRobo2 = robo2.getHealth();
        do {
            combatLog.add(performAttack(robo1, robo2));
            if (robo2.getHealth() <= 0) {
                combatLog.add(robo1.getName() + " " + CombatPhrases.WINNER.getPhrase());
                userService.addCurrency(ownerUsername1, 50); // Add currency to the winner
                robo1.setHealth(currentHealthRobo1);
                if (robo1.getHealth() < initialHealthRobo1) {
                    robo1.reduceStats(true);
                }
                robo2.setHealth(currentHealthRobo2);
                if (robo2.getHealth() < initialHealthRobo2) {
                    robo2.reduceStats(false);
                }
                break;
            }
            combatLog.add(performAttack(robo2, robo1));
            if (robo1.getHealth() <= 0) {
                combatLog.add(robo2.getName() + " " + CombatPhrases.WINNER.getPhrase());
                userService.addCurrency(ownerUsername2, 50); // Add currency to the winner////////////
                robo1.setHealth(currentHealthRobo1);
                if (robo1.getHealth() < initialHealthRobo1) {
                    robo1.reduceStats(false);
                }
                robo2.setHealth(currentHealthRobo2);
                if (robo2.getHealth() < initialHealthRobo2) {
                    robo2.reduceStats(true);
                }
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
                attackLog += "And " + String.format(defender.getName() + " " + CombatPhrases.COUNTER_ATTACK.getPhrase(), counterDamage);
            }
            return attackLog;
        } else {
            return attacker.getName() + " " + CombatPhrases.ATTACK_MISS.getPhrase();
        }
    }
}
package cat.itacademy.s05.t02.models;

import cat.itacademy.s05.t02.models.enums.CombatPhrases;
import cat.itacademy.s05.t02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FightSimulator {

    private final Random random = new Random();
    private final UserService userService;
    private final static int CURRENCY_REWARD = 12;

    @Autowired
    public FightSimulator(UserService userService) {
        this.userService = userService;
    }

    public List<String> simulateFight(Robo robo1, Robo robo2) {
        List<String> combatLog = new ArrayList<>();
        if (!robo1.canFight() || !robo2.canFight()) {
            combatLog.add(CombatPhrases.CANTFIGHT.getPhrase());
        } else {
            int initialHealthRobo1 = robo1.getHealth();
            int initialHealthRobo2 = robo2.getHealth();
            Robo firstAttacker = robo1.getSpeed() <= robo2.getSpeed() ? robo1 : robo2;
            Robo secondAttacker = firstAttacker == robo1 ? robo2 : robo1;

            boolean fightOngoing = true;
            do {
                String attackLog1 = performAttack(firstAttacker, secondAttacker);
                combatLog.add(attackLog1);

                if (secondAttacker.getHealth() <= 0) {
                    userService.addCurrency(firstAttacker.getUserId(), CURRENCY_REWARD);
                    userService.incrementWins(firstAttacker.getUserId());
                    fightOngoing = false;
                } else {
                    String attackLog2 = performAttack(secondAttacker, firstAttacker);
                    combatLog.add(attackLog2);

                    if (firstAttacker.getHealth() <= 0) {
                        userService.addCurrency(secondAttacker.getUserId(), CURRENCY_REWARD);
                        userService.incrementWins(secondAttacker.getUserId());
                        fightOngoing = false;
                    }
                }
            } while (fightOngoing && robo1.getHealth() > 0 && robo2.getHealth() > 0);

            resetHealthAndStats(robo1, initialHealthRobo1);
            resetHealthAndStats(robo2, initialHealthRobo2);
        }
        return combatLog;
    }


    private String performAttack(Robo attacker, Robo defender) {
        if (random.nextBoolean()) {
            int damage = random.nextInt(attacker.getAttack() + 1);
            defender.setHealth(Math.max(defender.getHealth() - damage, 0));
            String attackLog = String.format(attacker.getName() + " " + CombatPhrases.ATTACK_HIT.getPhrase(), damage);

            if (random.nextDouble() < 0.15 && defender.getHealth() > 0) {
                int counterDamage = random.nextInt(defender.getAttack() + 1);
                attacker.setHealth(Math.max(attacker.getHealth() - counterDamage, 0));
                attackLog += " And " + String.format(defender.getName() + " " + CombatPhrases.COUNTER_ATTACK.getPhrase(), counterDamage);
            }

            if (defender.getHealth() <= 0) {
                attackLog += "\n" + attacker.getName() + " " + CombatPhrases.WINNER.getPhrase() + "\n";
            } else if (attacker.getHealth() <= 0) {
                attackLog += "\n" + defender.getName() + " " + CombatPhrases.WINNER.getPhrase() + "\n";
            }

            return attackLog;
        } else {
            return attacker.getName() + " " + CombatPhrases.ATTACK_MISS.getPhrase();
        }
    }


    private void resetHealthAndStats(Robo robo, int initialHealth) {
        robo.setHealth(initialHealth);
        if (robo.getHealth() < initialHealth) {
            robo.reduceStats(false);
        } else {
            robo.reduceStats(true);
        }
    }
}
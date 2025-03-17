package cat.itacademy.s05.t02.Models;

import cat.itacademy.s05.t02.Models.Enums.CombatPhrases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FightSimulator {

    private final Random random = new Random();

    public List<String> simulateFight(Robo robo1, Robo robo2) {
        List<String> combatLog = new ArrayList<>();
        while (robo1.getHealth() > 0 && robo2.getHealth() > 0) {
            combatLog.add(performAttack(robo1, robo2));
            if (robo2.getHealth() <= 0) {
                combatLog.add(robo1.getName() + " " + CombatPhrases.WINNER.getPhrase());
                break;
            }
            combatLog.add(performAttack(robo2, robo1));
            if (robo1.getHealth() <= 0) {
                combatLog.add(robo2.getName() + " " + CombatPhrases.WINNER.getPhrase());
            }
        }
        return combatLog;
    }

    private String performAttack(Robo attacker, Robo defender) {
        if (random.nextBoolean()) {
            int damage = random.nextInt(attacker.getAttack());
            defender.setHealth(defender.getHealth() - damage);
            return String.format(attacker.getName() + " " + CombatPhrases.ATTACK_HIT.getPhrase(), damage);
        } else {
            return attacker.getName() + " " + CombatPhrases.ATTACK_MISS.getPhrase();
        }
    }
}
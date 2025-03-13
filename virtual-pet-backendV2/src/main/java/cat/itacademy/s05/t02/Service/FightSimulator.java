package cat.itacademy.s05.t02.Service;

import cat.itacademy.s05.t02.Models.Robo;

import java.util.Random;

public class FightSimulator {

    public String determineWinner(Robo robo1, Robo robo2) {
        Random random = new Random();
        int pet1Points = 0;
        int pet2Points = 0;

        if (random.nextInt(robo1.getHealth()) > random.nextInt(robo2.getHealth())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        if (random.nextInt(robo1.getAttack()) > random.nextInt(robo2.getAttack())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        if (random.nextInt(robo1.getDefense()) > random.nextInt(robo2.getDefense())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        if (random.nextInt(robo1.getSpeed()) > random.nextInt(robo2.getSpeed())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        return pet1Points > pet2Points ? robo1.getName() : robo2.getName();
    }
}
package cat.itacademy.s05.t02.service;

import cat.itacademy.s05.t02.Models.Pet;

import java.util.Random;

public class FightSimulator {

    public String determineWinner(Pet pet1, Pet pet2) {
        Random random = new Random();
        int pet1Points = 0;
        int pet2Points = 0;

        if (random.nextInt(pet1.getHealth()) > random.nextInt(pet2.getHealth())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        if (random.nextInt(pet1.getAttack()) > random.nextInt(pet2.getAttack())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        if (random.nextInt(pet1.getDefense()) > random.nextInt(pet2.getDefense())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        if (random.nextInt(pet1.getSpeed()) > random.nextInt(pet2.getSpeed())) {
            pet1Points++;
        } else {
            pet2Points++;
        }

        return pet1Points > pet2Points ? pet1.getName() : pet2.getName();
    }
}
package cat.itacademy.s05.t02.Models;

import cat.itacademy.s05.t02.Exceptions.PetTypeNotFoundException;
import cat.itacademy.s05.t02.Models.Enums.PetType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;
@Document(collection = "Pet")
public class Pet {
    @Id
    private String id;
    private String name;
    private PetType type;
    private int health;
    private int attack;
    private int defense;
    private int speed;
    private int happiness;
    private Long userId;

    public Pet(String name, PetType type, Long userId) {
        this.name = name;
        this.type = type;
        setStats(type);
        happiness = 100;
        this.userId = userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    private void setStats(PetType petType) {
        Random random = new Random();
        switch (petType) {
            case melee:
                this.health = 50 + random.nextInt(51); // 50 to 100
                this.attack = 70 + random.nextInt(31); // 70 to 100
                this.defense = 30 + random.nextInt(21); // 30 to 50
                this.speed = 85 + random.nextInt(16); // 85 to 100
                break;
            case ranged:
                this.health = 60 + random.nextInt(41); // 60 to 100
                this.attack = 60 + random.nextInt(41); // 60 to 100
                this.defense = 40 + random.nextInt(21); // 40 to 60
                this.speed = 60 + random.nextInt(41); // 60 to 100
                break;
            case tank:
                this.health = 100 + random.nextInt(51); // 100 to 150
                this.attack = 50 + random.nextInt(31); // 50 to 80
                this.defense = 70 + random.nextInt(31); // 70 to 100
                this.speed = 30 + random.nextInt(21); // 30 to 50
                break;
            default: throw new PetTypeNotFoundException("Invalid pet type: " + petType);
        }
    }
}

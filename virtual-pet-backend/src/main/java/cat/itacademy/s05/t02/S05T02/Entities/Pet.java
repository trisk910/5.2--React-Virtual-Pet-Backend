package cat.itacademy.s05.t02.S05T02.Entities;

import cat.itacademy.s05.t02.S05T02.Entities.Enums.PetType;
import cat.itacademy.s05.t02.S05T02.Exceptions.PetTypeNotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Random;
@Document(collection = "Pet")
public class Pet {
    @Id
    private Long id;
    private String name;
    private PetType type;
    private float health;
    private float attack;
    private float defense;
    private float speed;
    private float happiness;

    public Pet(String name,PetType type) {
        this.name = name;
        this.type = type;
        setStats(type);
        happiness = 100f;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public float getDefense() {
        return defense;
    }

    public void setDefense(float defense) {
        this.defense = defense;
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private void setStats(PetType petType) {
        Random random = new Random();
        switch (petType) {
            case melee:
                this.health = 50 + random.nextFloat() * 50; // 50 to 100
                this.attack = 70 + random.nextFloat() * 30; // 70 to 100
                this.defense = 30 + random.nextFloat() * 20; // 30 to 50
                this.speed = 80 + random.nextFloat() * 20; // 80 to 100
                break;
            case ranged:
                this.health = 60 + random.nextFloat() * 40; // 60 to 100
                this.attack = 60 + random.nextFloat() * 40; // 60 to 100
                this.defense = 40 + random.nextFloat() * 20; // 40 to 60
                this.speed = 60 + random.nextFloat() * 40; // 60 to 100
                break;
            case tank:
                this.health = 100 + random.nextFloat() * 50; // 100 to 150
                this.attack = 50 + random.nextFloat() * 30; // 50 to 80
                this.defense = 70 + random.nextFloat() * 30; // 70 to 100
                this.speed = 30 + random.nextFloat() * 20; // 30 to 50
                break;
            default: throw new PetTypeNotFoundException("Invalid pet type: " + petType);
        }
    }
}

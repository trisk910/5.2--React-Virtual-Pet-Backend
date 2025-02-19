package cat.itacademy.s05.t02.S05T02.Entities;

import cat.itacademy.s05.t02.S05T02.Entities.Enums.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "User")
public class User {
    private String name;
    private String username;
    private String email;
    private String password;
    private UserType userType;
    @Id
    private long id;
    private List<Pet> pets;

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
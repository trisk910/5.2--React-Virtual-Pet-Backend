package cat.itacademy.s05.t02.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Fights")
public class Fight {
    @Id
    private String id;
    private String pet1Name;
    private String pet2Name;
    private String winner;

    public Fight() {}

    public Fight(String pet1Name, String pet2Name, String winner) {
        this.pet1Name = pet1Name;
        this.pet2Name = pet2Name;
        this.winner = winner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPet1Name() {
        return pet1Name;
    }

    public void setPet1Name(String pet1Name) {
        this.pet1Name = pet1Name;
    }

    public String getPet2Name() {
        return pet2Name;
    }

    public void setPet2Name(String pet2Name) {
        this.pet2Name = pet2Name;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
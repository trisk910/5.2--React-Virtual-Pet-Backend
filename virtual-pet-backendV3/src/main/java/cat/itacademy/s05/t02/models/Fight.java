package cat.itacademy.s05.t02.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Fights")
public class Fight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String robo1Name;
    private String robo2Name;
    private String winner;

    public Fight() {}

    public Fight(String robo1Name, String robo2Name, String winner) {
        this.robo1Name = robo1Name;
        this.robo2Name = robo2Name;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRobo1Name() {
        return robo1Name;
    }

    public void setRobo1Name(String robo1Name) {
        this.robo1Name = robo1Name;
    }

    public String getRobo2Name() {
        return robo2Name;
    }

    public void setRobo2Name(String robo2Name) {
        this.robo2Name = robo2Name;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
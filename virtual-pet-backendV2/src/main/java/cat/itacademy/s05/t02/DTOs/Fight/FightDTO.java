package cat.itacademy.s05.t02.DTOs.Fight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightDTO {
    private String id;
    private String robo1Name;
    private String robo2Name;
    private String winner;
}
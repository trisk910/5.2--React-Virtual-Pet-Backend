package cat.itacademy.s05.t02.dtos.fight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightDTO {
    private Long id;
    private String robo1Name;
    private String robo2Name;
    private String winner;
    private List<String> combatLog;
}
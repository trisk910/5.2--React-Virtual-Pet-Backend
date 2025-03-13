package cat.itacademy.s05.t02.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFightDTO {
    private String robo1Id;
    private String robo2Id;
}
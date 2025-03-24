package cat.itacademy.s05.t02.dtos.robo;

import cat.itacademy.s05.t02.models.enums.RoboType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoboDTO {
    private String name;
    private RoboType type;
    private Long userId;
}
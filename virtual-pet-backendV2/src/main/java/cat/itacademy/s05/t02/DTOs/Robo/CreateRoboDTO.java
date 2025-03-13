package cat.itacademy.s05.t02.DTOs.Robo;

import cat.itacademy.s05.t02.Models.Enums.RoboType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoboDTO {
    private String name;
    private RoboType type;
    private String userId;
}
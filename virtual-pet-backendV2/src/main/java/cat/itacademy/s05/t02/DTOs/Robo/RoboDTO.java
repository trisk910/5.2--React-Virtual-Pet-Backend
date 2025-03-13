package cat.itacademy.s05.t02.DTOs.Robo;

import cat.itacademy.s05.t02.Models.Enums.RoboType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoboDTO {
    private Long id;
    private String name;
    private RoboType type;
    private Long userId;
}
package cat.itacademy.s05.t02.DTOs;

import cat.itacademy.s05.t02.Models.Enums.RoboType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoboDTO {
    private String id;
    private String name;
    private RoboType type;
    private String userId;
}
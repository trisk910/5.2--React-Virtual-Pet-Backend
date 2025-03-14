package cat.itacademy.s05.t02.DTOs.Robo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRobosDTO {
    private Long userId;
    private List<RoboResponseDTO> robos;

    public UserRobosDTO(Long userId, List<RoboResponseDTO> robos) {
        this.userId = userId;
        this.robos = robos;
    }
}
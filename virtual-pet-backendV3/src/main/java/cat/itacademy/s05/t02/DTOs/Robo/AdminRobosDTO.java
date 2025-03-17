package cat.itacademy.s05.t02.DTOs.Robo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRobosDTO {
    private int totalRobots;
    private List<RoboResponseDTO> robos;
}
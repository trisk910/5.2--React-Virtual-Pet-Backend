package cat.itacademy.s05.t02.dtos.robo;

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
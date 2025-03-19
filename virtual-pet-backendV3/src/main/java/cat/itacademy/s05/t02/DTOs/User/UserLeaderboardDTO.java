package cat.itacademy.s05.t02.DTOs.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLeaderboardDTO {
    private long id;
    private String username;
    private int wins;
}

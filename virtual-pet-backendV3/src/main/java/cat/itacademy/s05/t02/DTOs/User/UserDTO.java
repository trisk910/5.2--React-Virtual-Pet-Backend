package cat.itacademy.s05.t02.DTOs.User;

import cat.itacademy.s05.t02.Models.Enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private RoleType roleType;
    private String profileImage;
    private int currency;
}
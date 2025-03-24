package cat.itacademy.s05.t02.dtos.user;

import java.io.Serializable;

public class UserResponseDTO implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final UserDTO user;

	public UserResponseDTO(String jwttoken, UserDTO user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public UserDTO getUser() {
		return this.user;
	}
}

package cat.itacademy.s05.t02.DTOs.User;

import java.io.Serializable;

public class UserResponseDTO implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public UserResponseDTO(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
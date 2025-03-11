package cat.itacademy.s05.t02;

import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;

@SpringBootApplication
@OpenAPIDefinition
public class S05T02Application {

	public static void main(String[] args) {

		SpringApplication.run(S05T02Application.class, args);
		/*SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println("Clave segura para application.properties: " + base64Key);*/

	}

}

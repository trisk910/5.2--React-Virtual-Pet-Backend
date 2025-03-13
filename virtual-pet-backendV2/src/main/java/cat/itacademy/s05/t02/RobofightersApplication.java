package cat.itacademy.s05.t02;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class RobofightersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobofightersApplication.class, args);
	}

}

package cat.itacademy.s05.t02.S05T02.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Virtual Pet API");
        Info information = new Info()
                .title("Virtual Pet")
                .version("1.0")
                .description("This API exposes endpoints to manage a blackjack game.");
        return new OpenAPI().info(information).servers(List.of(server));
    }
}

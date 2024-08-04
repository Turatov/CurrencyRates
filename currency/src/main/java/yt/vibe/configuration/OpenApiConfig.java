package yt.vibe.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Currency rates api")
                        .description("This  api provides actual currency rates")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("You can find a source code here")
                        .url("https://github.com/Turatov/CurrencyRates"));
    }
}

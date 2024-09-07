package yt.vibe.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "project.properties")
public class PropertiesConfiguration {
    private String baseUrl;
    private String token;
    private String putRequestUri;
}


package yt.vibe.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "project.properties")
public class PropertiesConfiguration {
    private String baseUrl;
    private String token;
    private List<String> whitelistPaths;
}


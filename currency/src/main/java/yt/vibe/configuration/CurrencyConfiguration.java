package yt.vibe.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import yt.vibe.Service.CurrencyService;

@Configuration
public class CurrencyConfiguration {

    @Autowired
    private final CurrencyService currencyService;

    public CurrencyConfiguration(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}

package yt.vibe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import yt.vibe.currency.CurrencyAddingRequest;
import yt.vibe.currency.CurrencyClient;

@Configuration
public class CurrencyConfiguration {

    @Autowired
    private final CurrencyService currencyService;

    public CurrencyConfiguration(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public CurrencyClient currencyClient(){
        return new CurrencyClient() {
            @Override
            public void addCurrency(CurrencyAddingRequest currencyAddingRequest) {
                currencyService.addCurrency(currencyAddingRequest);
            }
        };
    }
}

package yt.vibe.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yt.vibe.entities.Currency;
import yt.vibe.dto.CurrencyAddingRequest;
import yt.vibe.configuration.PropertiesConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Getter

@Service
public class FreeCurrencyApiService {


    private final CurrencyService currencyService;
    private final PropertiesConfiguration propertiesConfiguration;
    private final RestTemplate restTemplate;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Autowired
    public FreeCurrencyApiService(PropertiesConfiguration propertiesConfiguration, RestTemplate restTemplate, CurrencyService currencyService) {
        this.propertiesConfiguration = propertiesConfiguration;
        this.restTemplate = restTemplate;
        this.currencyService = currencyService;
    }

    public void getRates() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jSon = restTemplate.getForObject(propertiesConfiguration.getBaseUrl(), String.class);
        try {
            Map<String, Map<String, Double>> allCurrencyRates = objectMapper.readValue(jSon, new TypeReference<Map<String, Map<String, Double>>>() {
            });
            addAllCurrencies(allCurrencyRates);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void addAllCurrencies(Map<String, Map<String, Double>> currencies) {
        List<Future<String>> futures = new ArrayList<>();
        currencies.get("data").forEach((code, rate) -> {
            Future<String> future = executorService.submit(() -> {
                currencyService.updateCurrencyByCode(new Currency(code, rate));
                return "Success : " + code + rate;
            });
            futures.add(future);
        });

        futures.forEach(future -> {
            try {
                String result = future.get();
                log.info("Task completed with result: " + result);
            } catch (Exception e) {
                log.error("Error occurred: " + e.getMessage());
            }
        });
    }
}



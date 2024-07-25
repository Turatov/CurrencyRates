package yt.vibe.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yt.vibe.CurrencyAddingRequest;
import yt.vibe.configuration.PropertiesConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter

@Service
public class FreeCurrencyApiService {

    private final PropertiesConfiguration propertiesConfiguration;
    private final RestTemplate restTemplate;

    @Autowired
    public FreeCurrencyApiService(PropertiesConfiguration propertiesConfiguration, RestTemplate restTemplate) {
        this.propertiesConfiguration = propertiesConfiguration;
        this.restTemplate = restTemplate;
    }

    public void getRates() throws JsonProcessingException {
        List<Map<String, Double>> ratesResponce = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String jSon = restTemplate.getForObject(propertiesConfiguration.getBaseUrl(), String.class);
        try {
            Map<String, Map<String, Double>> currencyMap = objectMapper.readValue(jSon, new TypeReference<Map<String, Map<String, Double>>>() {
            });
            addAllCurrencies(currencyMap);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void addAllCurrencies(Map<String, Map<String, Double>> currencies) {
        currencies.get("data").forEach((s, stringDoubleMap) -> {
            try {
                sendPostRequest((new CurrencyAddingRequest(s, stringDoubleMap)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendPostRequest(CurrencyAddingRequest currencyAddingRequest) throws JsonProcessingException {

        String url = "http://localhost:8080/api";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CurrencyAddingRequest> entity = new HttpEntity<>(currencyAddingRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println("Response: " + response.getBody());
    }

}

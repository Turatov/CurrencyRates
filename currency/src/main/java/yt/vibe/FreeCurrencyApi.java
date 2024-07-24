package yt.vibe;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
//@AllArgsConstructor
@Component
public class FreeCurrencyApi {
    private final String token =  "fca_live_lQrLEGyHXAK0mFS5X0h5qNc2myUxh3KR8DtQF65B";
    private final String baseUrl = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_lQrLEGyHXAK0mFS5X0h5qNc2myUxh3KR8DtQF65B";

    private final RestTemplate restTemplate ;

    public FreeCurrencyApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//List<Map<String,Double>>
    public void getRates() throws JsonProcessingException {
        List<Map<String,Double>> ratesResponce = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String jSon = restTemplate.getForObject(getBaseUrl(),String.class);
//        Map<String, Object> result = null;
        try {
            Map<String, Map<String, Double>> currencyMap = objectMapper.readValue(jSon, new TypeReference<Map<String, Map<String, Double>>>(){});
        System.out.println(currencyMap.get("data").get("EUR"));
        } catch (IOException e) {
            e.printStackTrace();
        } // исключения под контролем
    }
}

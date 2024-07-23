package yt.vibe;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
    public void getRates(){
        ObjectMapper mapper = new ObjectMapper();
        try {
          String response = restTemplate.getForObject(getBaseUrl(), String.class);
            JsonNode obj = mapper.readTree(response);
            System.out.println("Response: " + obj.get("data"));
//            return response;
        } catch (HttpClientErrorException | JsonProcessingException e) {
            System.err.println("Error: " + e.getMessage());
        }
//        return response;
    }



}

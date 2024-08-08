package yt.vibe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yt.vibe.Currency;
import yt.vibe.ScheduledCurrencyRates;
import yt.vibe.dto.CurrencyAddingRequest;
import yt.vibe.repository.ScheduleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduledCurrencyService {
    private final ScheduleRepository scheduleRepository;
    private final CurrencyService currencyService;
    private final RestTemplate restTemplate;

    public void addScheduledCurrency(ScheduledCurrencyRates scheduledCurrencyRates) {
        scheduleRepository.save(scheduledCurrencyRates);
    }

    public ResponseEntity<String> syncWithMainTable() {
        List<ScheduledCurrencyRates> scheduleRepositoryAll = scheduleRepository.findAll();
        if (!scheduleRepositoryAll.isEmpty()) {
            System.out.println(scheduleRepositoryAll);
            scheduleRepositoryAll.forEach(c -> {
                try {
                    sendPutRequest(new CurrencyAddingRequest(c.getCode(), c.getRate()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            });
        }
        return ResponseEntity.ok().body("All good");
    }

    public void sendPutRequest(CurrencyAddingRequest currencyAddingRequest) throws JsonProcessingException {
        String url = "http://localhost:8080/api";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Currency> entity = new HttpEntity<>(currencyAddingRequest.getCurrency(), headers);
        System.out.println(entity);
        restTemplate.put(url, entity);
    }
}

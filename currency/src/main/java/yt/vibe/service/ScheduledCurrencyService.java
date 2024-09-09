package yt.vibe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yt.vibe.Currency;
import yt.vibe.ScheduledCurrencyRates;
import yt.vibe.configuration.PropertiesConfiguration;
import yt.vibe.dto.CurrencyAddingRequest;
import yt.vibe.repository.ScheduleRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduledCurrencyService {
    private final ScheduleRepository scheduleRepository;
    private final CurrencyService currencyService;
    private RestTemplate restTemplate;
    PropertiesConfiguration propertiesConfiguration;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void addScheduledCurrency(ScheduledCurrencyRates scheduledCurrencyRates) {
        scheduleRepository.save(scheduledCurrencyRates);
    }


    public ResponseEntity<String> syncWithMainTable(ZonedDateTime dateTime) {
        List<ScheduledCurrencyRates> allExistedScheduledCurrencyRates = scheduleRepository.findBydatetimeEquals(dateTime);
        List<Future<String>> futures = new ArrayList<>();
        if (!allExistedScheduledCurrencyRates.isEmpty()) {
            allExistedScheduledCurrencyRates.forEach(c -> {
                Future<String> future = executorService.submit(() -> {
                    try {
                        sendPutRequestToCurrencyController(new CurrencyAddingRequest(c.getCode(), c.getRate()));
                        return "Success" + c.getCode() + c.getRate();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
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
        return ResponseEntity.ok().body("All good");
    }


    public void sendPutRequestToCurrencyController(CurrencyAddingRequest currencyAddingRequest) throws JsonProcessingException {
        String url = propertiesConfiguration.getPutRequestUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Currency> entity = new HttpEntity<>(currencyAddingRequest.getCurrency(), headers);
        restTemplate.put(url, entity);
    }
}

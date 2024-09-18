package yt.vibe.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yt.vibe.entities.Currency;
import yt.vibe.entities.ScheduledCurrencyRate;
import yt.vibe.repository.ScheduleRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void addScheduledCurrency(ScheduledCurrencyRate scheduledCurrencyRates) {
        scheduleRepository.save(scheduledCurrencyRates);
    }


    public void syncWithMainTable(ZonedDateTime dateTime) {
        List<ScheduledCurrencyRate> allExistedScheduledCurrencyRates =
                scheduleRepository.findBydatetimeEquals(dateTime);
        List<Future<String>> futures = new ArrayList<>();
        if (!allExistedScheduledCurrencyRates.isEmpty()) {
            allExistedScheduledCurrencyRates.forEach(scRate -> {
                Future<String> future = executorService.submit(() -> {
                    currencyService.updateCurrencyByCode(new Currency(scRate.getCode(), scRate.getRate()));
                    return "Success" + scRate.getCode() + scRate.getRate();
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
}

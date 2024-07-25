package yt.vibe.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yt.vibe.Currency;
import yt.vibe.CurrencyAddingRequest;
import yt.vibe.CurrencyRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public void addCurrency(CurrencyAddingRequest currencyAddingRequest) {
        currencyRepository.save(Currency.builder()
                .name(currencyAddingRequest.name())
                .rate(currencyAddingRequest.rate())
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}


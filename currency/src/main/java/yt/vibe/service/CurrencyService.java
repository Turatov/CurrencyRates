package yt.vibe.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yt.vibe.Currency;
import yt.vibe.dto.CurrencyAddingRequest;
import yt.vibe.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public void addCurrency(CurrencyAddingRequest currencyAddingRequest) {
        Optional<Currency> optionalCurrency = currencyRepository.findByCode(currencyAddingRequest.getCurrency().getCode());
        optionalCurrency.ifPresent(
                currency -> {
                    if (currencyAddingRequest.getCurrency().getCode().equals(currency.getCode())) {
                        throw new IllegalStateException(String.format("Currency with this code [%s] is already exist", currencyAddingRequest.getCurrency().getCode()));
                    } else
                        return;
                }
        );
        currencyRepository.save(Currency.builder()
                .code(currencyAddingRequest.getCurrency().getCode())
                .rate(currencyAddingRequest.getCurrency().getRate())
                .build());
    }

    public Currency getCurrencyByCode(String code) {
        Optional<Currency> optionalCurrency = currencyRepository.findByCode(code);
        System.out.println(optionalCurrency);
        return optionalCurrency.orElse(new Currency("NULL", 0.0));
    }


    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public void updateCurrencyByCode(Currency newCurrency) {
        currencyRepository.saveOrUpdateCurrency(newCurrency.getCode(), newCurrency.getRate());
    }
}


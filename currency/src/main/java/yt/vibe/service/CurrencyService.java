package yt.vibe.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yt.vibe.entities.Currency;
import yt.vibe.dto.CurrencyDto;
import yt.vibe.dto.CurrencyRateCsvRepresentation;
import yt.vibe.repository.CurrencyRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public void addCurrency(CurrencyDto currencyDto) {
        Optional<Currency> optionalCurrency = currencyRepository.findByCode(currencyDto.getCurrency().getCode());
        optionalCurrency.ifPresent(
            currency -> {
                if (currencyDto.getCurrency().getCode().equals(currency.getCode())) {
                    throw new IllegalStateException(
                                String.format("Currency with this code [%s] is already exist",
                                        currencyDto.getCurrency().getCode()));
                } else {
                    return;
                }
            }
        );
        currencyRepository.save(Currency.builder()
                .code(currencyDto.getCurrency().getCode())
                .rate(currencyDto.getCurrency().getRate())
                .build());
    }

    public Currency getCurrencyByCode(String code) {
        Optional<Currency> optionalCurrency = currencyRepository.findByCode(code);
        return optionalCurrency.orElse(null);
    }


    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public void updateCurrencyByCode(Currency newCurrency) {
        currencyRepository.saveOrUpdateCurrency(newCurrency.getCode(), newCurrency.getRate());
    }

    public Integer uploadCurrencyRatesUsCvs(MultipartFile file) throws IOException {
        Set<Currency> currencies = parseCsv(file);
        currencyRepository.saveAll(currencies);
        return currencies.size();
    }

    private Set<Currency> parseCsv(MultipartFile file) throws IOException {
        Reader reader = new BufferedReader((new InputStreamReader(file.getInputStream())));
        HeaderColumnNameMappingStrategy<CurrencyRateCsvRepresentation>
                mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(CurrencyRateCsvRepresentation.class);
        CsvToBean<CurrencyRateCsvRepresentation> csvToBean = new CsvToBeanBuilder<CurrencyRateCsvRepresentation>(reader)
                .withMappingStrategy(mappingStrategy)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse()
                .stream()
                .map(csvLine -> new Currency(csvLine.getCode(), csvLine.getRate()))
                .collect(Collectors.toSet());
    }
}



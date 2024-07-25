package yt.vibe.Contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yt.vibe.Currency;
import yt.vibe.CurrencyAddingRequest;
import yt.vibe.Service.CurrencyService;
import yt.vibe.Service.FreeCurrencyApiService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api")
@AllArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    private final FreeCurrencyApiService freeCurrencyApi;

    @PostMapping
    public void addCurrency(@RequestBody CurrencyAddingRequest currencyAddingRequest) {
        log.info("new currency {}", currencyAddingRequest);
        currencyService.addCurrency(currencyAddingRequest);
    }

    @GetMapping()
    @ResponseBody
    public List<Currency> getAllCurrencies() throws JsonProcessingException {
        freeCurrencyApi.getRates();
        return currencyService.getAllCurrencies();
    }
}

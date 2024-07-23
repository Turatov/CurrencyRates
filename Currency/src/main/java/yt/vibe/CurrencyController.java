package yt.vibe;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api")
@AllArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @PostMapping
    public void addCurrency(@RequestBody CurrencyAddingRequest currencyAddingRequest){
        log.info("new currency {}", currencyAddingRequest);
        currencyService.addCurrency(currencyAddingRequest);
    }

    @GetMapping("/ping")
    @ResponseBody
    public List<Currency> getAllCurrencies(){
       return  currencyService.getAllCurrencies();
    }
}

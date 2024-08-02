package yt.vibe.Contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yt.vibe.Currency;
import yt.vibe.CurrencyAddingRequest;
import yt.vibe.Service.CurrencyService;
import yt.vibe.Service.FreeCurrencyApiService;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api")
@AllArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    private final FreeCurrencyApiService freeCurrencyApiService;


    //    @PostMapping
//    public ResponseEntity<Void> addCurrency(@RequestBody CurrencyAddingRequest currencyAddingRequest) {
//        log.info("new currency {}", currencyAddingRequest);
//        try {
//            System.out.println(currencyAddingRequest + " ++++++++");
//            currencyService.addCurrency(currencyAddingRequest);
//        } catch (IllegalStateException e) {
//
//            if (!e.getMessage().isEmpty()) {
//                System.out.println("LOOOOL  " + e.getMessage());
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
    @PostMapping
    public ResponseEntity<String> addCurrency(@RequestBody CurrencyAddingRequest request) {
        try {
            currencyService.addCurrency(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Currency added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding currency: " + e.getMessage());
        }
    }

    @GetMapping()
    @ResponseBody
    public List<Currency> getAllCurrencies() throws JsonProcessingException {
        freeCurrencyApiService.getRates();
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable String code) {
        Optional<Currency> currencyByCode = Optional.ofNullable(currencyService.getCurrencyByCode(code));
        return currencyByCode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Currency> updateCurrencyByCode(@RequestBody Currency newCurrencyData) {
        currencyService.updateCurrencyByCode(newCurrencyData);
        return ResponseEntity.ok(currencyService.getCurrencyByCode(newCurrencyData.getCode()));
    }
}

package yt.vibe.Contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping
    public ResponseEntity<String> addCurrency(@RequestBody CurrencyAddingRequest request) {
        try {
            currencyService.addCurrency(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Currency added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding currency: " + e.getMessage());
        }
    }

    @Operation(summary = "Get a list of actual rates for currencies ", description = "Returns a list of currencies")
    @GetMapping()
    @ResponseBody
    public List<Currency> getAllCurrencies() throws JsonProcessingException {
        freeCurrencyApiService.getRates();
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@Parameter(description = "Currency code to be  retrieved", example = "USD") @PathVariable String code) {
        Optional<Currency> currencyByCode = Optional.ofNullable(currencyService.getCurrencyByCode(code));
        return currencyByCode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Currency> updateCurrencyByCode(@RequestBody Currency newCurrencyData) {
        currencyService.updateCurrencyByCode(newCurrencyData);
        return ResponseEntity.ok(currencyService.getCurrencyByCode(newCurrencyData.getCode()));
    }
}

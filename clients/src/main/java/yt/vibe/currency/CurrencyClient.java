package yt.vibe.currency;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient("currency")
public interface CurrencyClient {
    @PostMapping("api")
     void addCurrency( CurrencyAddingRequest currencyAddingRequest);

//    @PostMapping"api"
//    public void updateCurrency(CurrencyAddingRequest CurrencyAddingRequest);
}

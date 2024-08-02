package yt.vibe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class CurrencyAddingRequest {
    private final Currency currency;

    @JsonCreator
    public CurrencyAddingRequest(
            @JsonProperty("code") String code,
            @JsonProperty("rate") Double rate) {
        this.currency = new Currency(code, rate);
    }

    @Override
    public String toString() {
        return "CustomerRegistrationRequest{" +
                "customer=" + currency +
                '}';
    }
}

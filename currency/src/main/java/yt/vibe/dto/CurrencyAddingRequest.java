package yt.vibe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import yt.vibe.entities.Currency;

import javax.validation.constraints.Min;

@Getter
@Data
public class CurrencyAddingRequest {
    private final Currency currency;

    @JsonCreator
    public CurrencyAddingRequest(
            @Schema(hidden = true)
            @JsonProperty("code") String code,
            @Schema(hidden = true)
            @Min(value = 0, message = "Rate must be positive")
            @JsonProperty("rate") Double rate) {
        this.currency = new Currency(code, rate);
    }
}

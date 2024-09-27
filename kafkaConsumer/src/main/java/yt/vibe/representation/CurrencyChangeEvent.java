package yt.vibe.representation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyChangeEvent {
    private CurrencyData before;
    private CurrencyData after;
    private String op;
    private Long timeStampMs;
}

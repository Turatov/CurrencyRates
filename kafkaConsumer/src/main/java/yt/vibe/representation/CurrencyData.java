package yt.vibe.representation;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CurrencyData {
    private Integer id;
    private String code;
    private Double rate;
}

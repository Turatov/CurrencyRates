package yt.vibe.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyRateCsvRepresentation {
    private String code;
    private Double rate;
}

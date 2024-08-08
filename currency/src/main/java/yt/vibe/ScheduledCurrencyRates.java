package yt.vibe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScheduledCurrencyRates {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Getter
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime datetime;


    @Schema(description = "Unique identifier of the currency", example = "USD")
//    @Column(unique = true)
    private String code;
    @Schema(description = "Currency rate ", example = "90.8")
    private Double rate;

    public ScheduledCurrencyRates(String code, Double rate, ZonedDateTime datetime) {
        this.code = code;
        this.rate = rate;
        this.datetime = datetime;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }


}
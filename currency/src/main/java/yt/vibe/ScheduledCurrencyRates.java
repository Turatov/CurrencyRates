package yt.vibe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScheduledCurrencyRates {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

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

    public ZonedDateTime getDatetime() {
        return datetime.minusHours(1);
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public String getJobName() {
        return "Job for : " + getDatetime().toString();
    }

    public String getTriggerName() {
        return "Trigger for : " + getDatetime().toString();
    }
}
package yt.vibe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Data
public class ScheduledCurrencyDTO {


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Schema(description = "Trigger time to schedule with time zone", example = "2024-08-08T19:37:00.259Z")
    private ZonedDateTime datetime;
    @Schema(description = "Array for currencies", example = "{\"USD\":\"98.2\"}")
    private Map<String, Double> data;

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public ZonedDateTime getDatetime() {
        return datetime.minusHours(1);
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }

    @Schema(hidden = true)
    public String getJobName() {
        return "Job for : " + getDatetime().toString();
    }

    @Schema(hidden = true)
    public String getTriggerName() {
        return "Trigger for : " + getDatetime().toString();
    }
}

package yt.vibe.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ScheduleRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime datetime;


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
package yt.vibe.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Component
public class CurrencyUpdateJob implements Job {

    private final ScheduledCurrencyService scheduledCurrencyService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        assert scheduledCurrencyService != null;
        scheduledCurrencyService.syncWithMainTable(context.getTrigger().getStartTime().toInstant().atZone(ZoneId.systemDefault()));
    }
}
package yt.vibe.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Component
public class CurrencyUpdateJob implements Job {

    private final ScheduledCurrencyService scheduledCurrencyService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("Updating currency rates...");
        assert scheduledCurrencyService != null;
        System.out.println(scheduledCurrencyService.syncWithMainTable());

    }
}
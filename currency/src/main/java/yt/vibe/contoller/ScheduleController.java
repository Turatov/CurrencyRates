package yt.vibe.contoller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yt.vibe.service.CurrencyUpdateJob;
import yt.vibe.ScheduledCurrencyRates;
import yt.vibe.service.ScheduledCurrencyService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/schedule")
@AllArgsConstructor
public class ScheduleController {
    
    private Scheduler scheduler;
    private ScheduledCurrencyService scheduledCurrencyService;

    @PostMapping
    public String scheduleJob(@RequestBody ScheduledCurrencyRates scheduleRequest) {
        try {
            // Преобразование ZonedDateTime в Date
            Date startDate = Date.from(scheduleRequest.getDatetime().toInstant());

            // Создание JobDetail
            JobDetail jobDetail = JobBuilder.newJob(CurrencyUpdateJob.class)
                    .withIdentity(scheduleRequest.getJobName(), "group1")
                    .build();
            // Создание Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleRequest.getTriggerName(), "group12")
                    .startAt(startDate)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .build();
            // Планирование задачи
            System.out.println(scheduleRequest);
            scheduler.scheduleJob(jobDetail, trigger);
            scheduledCurrencyService.addScheduledCurrency(scheduleRequest);

            return "Job scheduled successfully for " + startDate.toString();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return "Error scheduling job: " + e.getMessage();
        }


    }

    @GetMapping("/jobs")
    public List<String> getAllJobs() {
        List<String> jobList = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    jobList.add("Job name: " + jobKey.getName() + ", Group: " + jobKey.getGroup());
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }
}
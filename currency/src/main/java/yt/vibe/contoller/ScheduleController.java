package yt.vibe.contoller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.web.bind.annotation.*;
import yt.vibe.dto.ScheduledCurrencyDTO;
import yt.vibe.ScheduledCurrencyRates;
import yt.vibe.service.CurrencyUpdateJob;
import yt.vibe.service.ScheduledCurrencyService;


import java.time.ZonedDateTime;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("api/schedule")
@AllArgsConstructor
public class ScheduleController {

    private Scheduler scheduler;
    private ScheduledCurrencyService scheduledCurrencyService;

    @Operation(summary = "Schedule currency rate", description = "Send json with new rate and datetime to schedule rates")
    @PostMapping
    public String scheduleJob(@RequestBody ScheduledCurrencyDTO scheduledCurrencyDTO) throws Exception {
        try {
            Date startDate = Date.from(scheduledCurrencyDTO.getDatetime().toInstant());

            JobDetail jobDetail = JobBuilder.newJob(CurrencyUpdateJob.class)
                    .withIdentity(scheduledCurrencyDTO.getJobName(), "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduledCurrencyDTO.getTriggerName(), "group1")
                    .startAt(startDate)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            ZonedDateTime dateTime = scheduledCurrencyDTO.getDatetime();
            Map<String, Double> map = scheduledCurrencyDTO.getData();
            map.forEach((c, k) -> {
                scheduledCurrencyService.addScheduledCurrency(new ScheduledCurrencyRates(c, k, dateTime));
            });

            return "Job scheduled successfully for " + startDate.toString();
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            return "Error scheduling job: " + e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Operation(summary = "Get scheduled jobs", description = "Returns list of active jobs")
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
            log.info(e.getMessage());
        }
        return jobList;
    }
}
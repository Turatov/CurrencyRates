package yt.vibe.contoller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.web.bind.annotation.*;
import yt.vibe.dto.ScheduledCurrencyDto;
import yt.vibe.entities.ScheduledCurrencyRate;
import yt.vibe.service.CurrencyUpdateJob;
import yt.vibe.service.ScheduledCurrencyService;


import java.time.ZonedDateTime;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("api/v1/schedule/admin")
@AllArgsConstructor
public class ScheduleController {

    private Scheduler scheduler;
    private ScheduledCurrencyService scheduledCurrencyService;

    @Operation(summary = "Schedule currency rate",
            description = "Send json with new rate and datetime to schedule rates")
    @PostMapping
    public String scheduleJob(@RequestBody ScheduledCurrencyDto scheduledCurrencyDto) throws Exception {
        try {
            Date startDate = Date.from(scheduledCurrencyDto.getDatetime().toInstant());

            JobDetail jobDetail = JobBuilder.newJob(CurrencyUpdateJob.class)
                    .withIdentity(scheduledCurrencyDto.getJobName(), "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduledCurrencyDto.getTriggerName(), "group1")
                    .startAt(startDate)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            ZonedDateTime dateTime = scheduledCurrencyDto.getDatetime();
            Map<String, Double> map = scheduledCurrencyDto.getData();
            map.forEach((code, rate) -> {
                scheduledCurrencyService.addScheduledCurrency(new ScheduledCurrencyRate(code, rate, dateTime));
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
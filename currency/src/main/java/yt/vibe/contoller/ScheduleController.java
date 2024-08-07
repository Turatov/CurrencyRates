package yt.vibe.contoller;


import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yt.vibe.service.CurrencyUpdateJob;
import yt.vibe.dto.ScheduleRequest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping("/sch")
public class ScheduleController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping
    public String scheduleJob(@RequestBody ScheduleRequest scheduleRequest) {
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
            scheduler.scheduleJob(jobDetail, trigger);

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
package yt.vibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yt.vibe.ScheduledCurrencyRates;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduledCurrencyRates, Long> {
    List<ScheduledCurrencyRates> findBydatetimeEquals(ZonedDateTime dateTime);
}

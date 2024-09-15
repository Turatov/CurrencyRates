package yt.vibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yt.vibe.entities.ScheduledCurrencyRate;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduledCurrencyRate, Long> {
    List<ScheduledCurrencyRate> findBydatetimeEquals(ZonedDateTime dateTime);
}

package yt.vibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yt.vibe.ScheduledCurrencyRates;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduledCurrencyRates, Long> {
}

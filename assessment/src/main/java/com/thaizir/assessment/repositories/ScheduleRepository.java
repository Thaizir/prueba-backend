package com.thaizir.assessment.repositories;

import com.thaizir.assessment.models.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Optional<Schedule> findByDateAndTime(LocalDate scheduleDate, LocalTime scheduleTime);

    List<Schedule> findAllByAvailableAndDateAfter(boolean b, LocalDate now);
}

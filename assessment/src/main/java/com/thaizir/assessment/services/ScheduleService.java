package com.thaizir.assessment.services;

import com.thaizir.assessment.models.Schedule;
import com.thaizir.assessment.repositories.ScheduleRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class CreateSlotRequest {

        private String date;
        private String time;

        public void isValidSlot(String time) {
            LocalTime scheduleTime = LocalTime.parse(time);
            LocalTime openingTime = LocalTime.of(12, 0);
            LocalTime closingTime = LocalTime.of(22, 0);

            if(scheduleTime.isBefore(openingTime) || scheduleTime.isAfter(closingTime)) {
                throw new RuntimeException("Slot must be between 12:00 and 22:00");
            }
        }
    }

    public List<Schedule> getAvailableSlots() {
        return this.scheduleRepository.findAllByAvailableAndDateAfter(true, LocalDate.now());
    }

    public Schedule createSlot(CreateSlotRequest createSlotRequest) {

        createSlotRequest.isValidSlot(createSlotRequest.time);

        if (scheduleRepository.findByDateAndTime(LocalDate.parse(createSlotRequest.date),
            LocalTime.parse(createSlotRequest.time)).isPresent()) {
            throw new RuntimeException("Slot already exists");
        }

        Schedule schedule = new Schedule();
        schedule.setDate(LocalDate.parse(createSlotRequest.date));
        schedule.setTime(LocalTime.parse(createSlotRequest.time));

        return scheduleRepository.save(schedule);
    }

    public void deleteSlot(int id) {
        scheduleRepository.deleteById(id);
    }

}

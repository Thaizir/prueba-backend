package com.thaizir.assessment.services;

import com.thaizir.assessment.models.Client;
import com.thaizir.assessment.models.Reservation;
import com.thaizir.assessment.models.Schedule;
import com.thaizir.assessment.repositories.ClientRepository;
import com.thaizir.assessment.repositories.ReservationRepository;
import com.thaizir.assessment.repositories.ScheduleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ReservationService {

    private final ClientRepository clientRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;


    @AllArgsConstructor
    @Getter
    @Setter
    public static class CreateReservationRequest {

        private String clientName;
        private String clientEmail;
        private String clientPhoneNumber;
        private String scheduleDate;
        private String scheduleTime;

        public void isValidDate(LocalDate date, LocalTime time) throws IllegalAccessException {

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            if (date.isBefore(currentDate) || (date.isEqual(currentDate) && time.isBefore(currentTime))) {
                throw new IllegalAccessException("Cannot create reservation in the past");
            }
        }

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class UpdateReservationRequest {

        private Schedule schedule;

        public LocalDate getScheduleDate() {
            return schedule.getDate();
        }

        public LocalTime getScheduleTime() {
            return schedule.getTime();
        }
    }

    @Transactional
    public Reservation createReservation(CreateReservationRequest createReservationRequest)
        throws IllegalAccessException {

        createReservationRequest.isValidDate(
            LocalDate.parse(createReservationRequest.scheduleDate),
            LocalTime.parse(createReservationRequest.scheduleTime)
        );

        Client client = this.clientRepository.findByEmail(createReservationRequest.clientEmail)
            .orElseGet(
                () -> {
                    Client newClient = new Client();
                    newClient.setName(createReservationRequest.getClientName());
                    newClient.setEmail(createReservationRequest.getClientEmail());
                    newClient.setPhoneNumber(createReservationRequest.getClientPhoneNumber());
                    return this.clientRepository.save(newClient);
                }
            );

        Schedule schedule = this.scheduleRepository.findByDateAndTime(
                LocalDate.parse(createReservationRequest.scheduleDate),
                LocalTime.parse(createReservationRequest.scheduleTime)).
            orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.isAvailable()) {
            throw new RuntimeException("Schedule is not available");
        }
        Reservation newReservation = new Reservation();
        newReservation.setClient(client);
        newReservation.setSchedule(schedule);

        schedule.setAvailable(false);
        scheduleRepository.save(schedule);
        return this.reservationRepository.save(newReservation);
    }

    public List<Reservation> getReservationPerDate(LocalDate date) {

        return this.reservationRepository.findReservationsByScheduleDate(date);
    }


    @Transactional
    public Reservation updateReservation(UpdateReservationRequest updateReservationRequest,
        int id) {

        if (updateReservationRequest.schedule.getDate() == null
            || updateReservationRequest.schedule.getTime() == null) {
            throw new RuntimeException("New schedule information is required");
        }

        Reservation dbReservation = this.reservationRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Reservation not found"));

        Schedule newSchedule = this.scheduleRepository.findByDateAndTime(
                updateReservationRequest.getScheduleDate(),
                updateReservationRequest.getScheduleTime())
            .orElseThrow(() -> new RuntimeException("New schedule slot not found"));

        if (!newSchedule.isAvailable()) {
            throw new RuntimeException("New schedule slot is not available");
        }

        Schedule oldSchedule = dbReservation.getSchedule();
        oldSchedule.setAvailable(true);
        scheduleRepository.save(oldSchedule);

        newSchedule.setAvailable(false);
        scheduleRepository.save(newSchedule);
        dbReservation.setSchedule(newSchedule);

        return reservationRepository.save(dbReservation);
    }

    public void deleteReservation(int id) {

        Reservation dbReservation = this.reservationRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Reservation not found"));
        Schedule schedule = dbReservation.getSchedule();
        schedule.setAvailable(true);
        scheduleRepository.save(schedule);
        reservationRepository.deleteById(id);
    }
}

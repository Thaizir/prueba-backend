package com.thaizir.assessment.controllers;

import com.thaizir.assessment.models.Reservation;
import com.thaizir.assessment.services.ReservationService;
import com.thaizir.assessment.services.ReservationService.CreateReservationRequest;
import com.thaizir.assessment.services.ReservationService.UpdateReservationRequest;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(
        @RequestBody CreateReservationRequest createReservationRequest) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.reservationService.createReservation(createReservationRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getReservationsPerDate(
        @RequestParam(required = true, name = "date") String date) {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(this.reservationService.getReservationPerDate(LocalDate.parse(date)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@RequestBody UpdateReservationRequest updateReservationRequest, @PathVariable int id) {

        try {
            return ResponseEntity.status(203)
                .body(this.reservationService.updateReservation(updateReservationRequest, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable int id) {

        try {
            this.reservationService.deleteReservation(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Reservation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

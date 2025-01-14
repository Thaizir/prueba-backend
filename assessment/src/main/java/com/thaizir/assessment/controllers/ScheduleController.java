package com.thaizir.assessment.controllers;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

import com.thaizir.assessment.services.ScheduleService;
import com.thaizir.assessment.services.ScheduleService.CreateSlotRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slots")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSlots() {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(this.scheduleService.getAvailableSlots());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSlot(@RequestBody CreateSlotRequest createSlotRequest) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.scheduleService.createSlot(createSlotRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSlot(@PathVariable int id) {

        try {
            this.scheduleService.deleteSlot(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Slot deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

package com.thaizir.assessment.repositories;

import com.thaizir.assessment.models.Reservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r JOIN r.schedule s WHERE s.date = :date")
    List<Reservation> findReservationsByScheduleDate(@Param("date") LocalDate date);
}

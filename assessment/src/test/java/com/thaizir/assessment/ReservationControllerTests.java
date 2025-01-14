package com.thaizir.assessment;

import com.thaizir.assessment.models.Client;
import com.thaizir.assessment.repositories.ClientRepository;
import com.thaizir.assessment.repositories.ReservationRepository;
import com.thaizir.assessment.repositories.ScheduleRepository;
import com.thaizir.assessment.services.ReservationService.CreateReservationRequest;
import com.thaizir.assessment.services.ScheduleService.CreateSlotRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ReservationControllerTests {

    @MockBean
    ClientRepository clientRepository;

    @MockBean
    ScheduleRepository scheduleRepository;

    @MockBean
    ReservationRepository reservationRepository;

    @DisplayName("Should create a reservation for valid data")
    @Test
    void shouldCreateReservation() throws Exception {

        Client mockClient = new Client();
        mockClient.setId(1);
        mockClient.setName("Thaizir");
        mockClient.setEmail("thaizire@protonmail.com");
        mockClient.setPhoneNumber("1234567890");

        var date = LocalDate.of(2025, 2, 22);
        var time = LocalTime.of(16, 0);

        APIInteraction apiInteraction = new APIInteraction();

        var createSlotResponse = apiInteraction.createSlot(
            new CreateSlotRequest(date.toString(), time.toString()));
        createSlotResponse.then().statusCode(201);
        int slotId = createSlotResponse.jsonPath().getInt("id");

        CreateReservationRequest createReservationRequest = new CreateReservationRequest(
            "Thaizir",
            "thaizire@protonmail.com",
            "1234567890",
            date.toString(),
            time.toString()
        );

        var createReservationResponse = apiInteraction.createReservation(createReservationRequest);
        createReservationResponse.then().statusCode(201);
        int reservationId = createReservationResponse.jsonPath().getInt("id");

        // deleting the slot and reservation so the test can be repeated
        var deleteReservationResponse = apiInteraction.deleteReservation(reservationId);
        deleteReservationResponse.then().statusCode(204);
        var deleteSlotResponse = apiInteraction.deleteSlot(slotId);
        deleteSlotResponse.then().statusCode(204);
    }

    @DisplayName("Should not create a reservation for the same time slot")
    @Test
    void shouldNotCreateReservationForTheSameTimeSlot() throws Exception {

        Client mockClient = new Client();
        mockClient.setId(1);
        mockClient.setName("Thaizir");
        mockClient.setEmail("thaizire@protonmail.com");
        mockClient.setPhoneNumber("1234567890");

        var date = LocalDate.of(2025, 2, 22);
        var time = LocalTime.of(16, 0);

        APIInteraction apiInteraction = new APIInteraction();

        var createSlotResponse = apiInteraction.createSlot(
            new CreateSlotRequest(date.toString(), time.toString()));
        createSlotResponse.then().statusCode(201);
        int slotId = createSlotResponse.jsonPath().getInt("id");

        CreateReservationRequest createReservationRequest = new CreateReservationRequest(
            "Thaizir",
            "thaizire@protonmail.com",
            "1234567890",
            date.toString(),
            time.toString()
        );

        CreateReservationRequest createReservationRequest1 = new CreateReservationRequest(
            "Thaizir2",
            "thaizire2@protonmail.com",
            "1234567891",
            date.toString(),
            time.toString()
        );

        var createReservationResponse = apiInteraction.createReservation(createReservationRequest);
        createReservationResponse.then().statusCode(201);
        int reservationId = createReservationResponse.jsonPath().getInt("id");

        var createReservationResponse1 = apiInteraction.createReservation(createReservationRequest);
        createReservationResponse1.then().statusCode(400);

        // deleting the slot and reservation so the test can be repeated
        var deleteReservationResponse = apiInteraction.deleteReservation(reservationId);
        deleteReservationResponse.then().statusCode(204);
        var deleteSlotResponse = apiInteraction.deleteSlot(slotId);
        deleteSlotResponse.then().statusCode(204);
    }


    @DisplayName("Should not create a reservation if date is in the past")
    @Test
    void shouldNotCreateReservationIfDateIsInThePast() throws Exception {

        Client mockClient = new Client();
        mockClient.setId(1);
        mockClient.setName("Thaizir");
        mockClient.setEmail("thaizire@protonmail.com");
        mockClient.setPhoneNumber("1234567890");

        var date = LocalDate.now().minusDays(10);
        var time = LocalTime.now();

        APIInteraction apiInteraction = new APIInteraction();

        var createSlotResponse = apiInteraction.createSlot(
            new CreateSlotRequest(date.toString(), time.toString()));
        createSlotResponse.then().statusCode(201);
        int slotId = createSlotResponse.jsonPath().getInt("id");

        CreateReservationRequest createReservationRequest = new CreateReservationRequest(
            "Thaizir",
            "thaizire@protonmail.com",
            "1234567890",
            date.toString(),
            time.toString()
        );

        var createReservationResponse = apiInteraction.createReservation(createReservationRequest);
        createReservationResponse.then().statusCode(400);

        // deleting the slot so the test can be repeated
        var deleteSlotResponse = apiInteraction.deleteSlot(slotId);
        deleteSlotResponse.then().statusCode(204);
    }


    @DisplayName("Should not create a reservation if date is today and time is in the past")
    @Test
    void shouldNotCreateReservationIfDateIsTodayAndTimeIsInThePast() throws Exception {

        Client mockClient = new Client();
        mockClient.setId(1);
        mockClient.setName("Thaizir");
        mockClient.setEmail("thaizire@protonmail.com");
        mockClient.setPhoneNumber("1234567890");

        var date = LocalDate.now();
        var time = LocalTime.now().minusHours(2);

        APIInteraction apiInteraction = new APIInteraction();

        var createSlotResponse = apiInteraction.createSlot(
            new CreateSlotRequest(date.toString(), time.toString()));
        createSlotResponse.then().statusCode(201);
        int slotId = createSlotResponse.jsonPath().getInt("id");

        CreateReservationRequest createReservationRequest = new CreateReservationRequest(
            "Thaizir",
            "thaizire@protonmail.com",
            "1234567890",
            date.toString(),
            time.toString()
        );

        var createReservationResponse = apiInteraction.createReservation(createReservationRequest);
        createReservationResponse.then().statusCode(400);

        // deleting the slot so the test can be repeated
        var deleteSlotResponse = apiInteraction.deleteSlot(slotId);
        deleteSlotResponse.then().statusCode(204);
    }

}

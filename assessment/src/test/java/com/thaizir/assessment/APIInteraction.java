package com.thaizir.assessment;

import com.thaizir.assessment.services.ReservationService.CreateReservationRequest;
import com.thaizir.assessment.services.ReservationService.UpdateReservationRequest;
import com.thaizir.assessment.services.ScheduleService.CreateSlotRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
public class APIInteraction {


    public Response createReservation(
        CreateReservationRequest createReservationRequest
    ) throws Exception {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType("application/json");

        return RestAssured.given()
            .spec(requestSpecBuilder.build())
            .body(createReservationRequest)
            .post("/reservations/create")
            .andReturn();
    }

    public Response getReservationsPerDate(String date) throws Exception {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType("application/json");

        return RestAssured.given()
            .spec(requestSpecBuilder.build())
            .params("date", date)
            .get("/reservations/search")
            .andReturn();
    }

    public Response updateReservation(
        UpdateReservationRequest updateReservationRequest,
        int id
    ) throws Exception {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType("application/json");

        return RestAssured.given()
            .spec(requestSpecBuilder.build())
            .body(updateReservationRequest)
            .put("/reservations/" + id)
            .andReturn();
    }

    public Response deleteReservation(int id) throws Exception {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType("application/json");

        return RestAssured.given()
            .spec(requestSpecBuilder.build())
            .delete("/reservations/" + id)
            .andReturn();
    }


    public Response createSlot(CreateSlotRequest createSlotRequest) throws Exception {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType("application/json");

        return RestAssured.given()
            .spec(requestSpecBuilder.build())
            .body(createSlotRequest)
            .post("/slots/create")
            .andReturn();
    }

    public Response deleteSlot(int id) throws Exception {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType("application/json");

        return RestAssured.given()
            .spec(requestSpecBuilder.build())
            .delete("/slots/" + id)
            .andReturn();

    }
}

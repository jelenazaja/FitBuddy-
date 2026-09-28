package com.jelenazaja.fitbuddy.workout_service;

import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private WorkoutService workoutService;

    @BeforeEach
    void setUpRestClient() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .defaultStatusHandler(
                        status -> !status.is2xxSuccessful(),
                        (request, response) -> {
                        }
                )
                .build();
    }

    @Test
    void shouldReturnAllWorkouts() {
        when(workoutService.findAll())
                .thenReturn(List.of());

        var response = restClient.get()
                .uri("/workouts")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnWorkoutById() {
        when(workoutService.findById(1L))
                .thenReturn(null);

        var response = restClient.get()
                .uri("/workouts/1")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldCreateWorkout() {
        when(workoutService.create(any()))
                .thenReturn(null);

        var request = """
                {
                  "name": "Leg day",
                  "date": "2026-07-07",
                  "durationMinutes": 60,
                  "note": "Glutes and legs workout"
                }
                """;

        var response = restClient.post()
                .uri("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void shouldReturnBadRequestWhenWorkoutNameIsBlank() {
        var request = """
                {
                  "name": "",
                  "date": "2026-07-07",
                  "durationMinutes": 60,
                  "note": "Leg workout"
                }
                """;

        var response = restClient.post()
                .uri("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequestWhenDateIsMissing() {
        var request = """
                {
                  "name": "Leg day",
                  "durationMinutes": 60,
                  "note": "Leg workout"
                }
                """;

        var response = restClient.post()
                .uri("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequestWhenDurationIsNegative() {
        var request = """
                {
                  "name": "Leg day",
                  "date": "2026-07-07",
                  "durationMinutes": -60,
                  "note": "Leg workout"
                }
                """;

        var response = restClient.post()
                .uri("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }
}

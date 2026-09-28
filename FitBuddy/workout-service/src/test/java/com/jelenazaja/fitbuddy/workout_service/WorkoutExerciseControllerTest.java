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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutExerciseControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private WorkoutService workoutExerciseService;

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
    void shouldReturnWorkoutExercises() {
        when(workoutExerciseService.getWorkoutExercises(1L))
                .thenReturn(List.of());

        var response = restClient.get()
                .uri("/workouts/1/workout_exercises")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    /*@Test
    void shouldAddExerciseToWorkout() {
        when(workoutExerciseService.addExerciseToWorkout(eq(1L), any()))
                .thenReturn(null);

        var request = """
                {
                  "exerciseId": 2,
                  "orderIndex": 1,
                  "notes": "Warm up first"
                }
                """;

        var response = restClient.post()
                .uri("/workouts/1/workout-exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(201);
    }*/

    @Test
    void shouldReturnBadRequestWhenExerciseIdIsMissing() {
        var request = """
                {
                  "orderIndex": 1,
                  "notes": "Warm up first"
                }
                """;

        var response = restClient.post()
                .uri("/workouts/1/workout_exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequestWhenOrderIndexIsNegative() {
        var request = """
                {
                  "exerciseId": 2,
                  "orderIndex": -1,
                  "notes": "Warm up first"
                }
                """;

        var response = restClient.post()
                .uri("/workouts/1/workout_exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }
}
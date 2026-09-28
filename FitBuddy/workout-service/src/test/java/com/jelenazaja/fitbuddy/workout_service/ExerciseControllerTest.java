package com.jelenazaja.fitbuddy.workout_service;

import com.jelenazaja.fitbuddy.workout_service.exercise.ExerciseService;
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
class ExerciseControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private ExerciseService exerciseService;

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
    void shouldReturnAllExercises() {
        when(exerciseService.findAll())
                .thenReturn(List.of());

        var response = restClient.get()
                .uri("/exercises")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnExerciseById() {
        when(exerciseService.findById(1L))
                .thenReturn(null);

        var response = restClient.get()
                .uri("/exercises/1")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldCreateExercise() {
        when(exerciseService.create(any()))
                .thenReturn(null);

        var request = """
                {
                  "name": "Squat",
                  "muscleGroup": "LEGS",
                  "equipment": "BARBELL",
                  "description": "Compound leg exercise"
                }
                """;

        var response = restClient.post()
                .uri("/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void shouldReturnBadRequestWhenExerciseNameIsBlank() {
        var request = """
                {
                  "name": "",
                  "muscleGroup": "LEGS",
                  "equipment": "BARBELL",
                  "description": "Compound leg exercise"
                }
                """;

        var response = restClient.post()
                .uri("/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequestWhenMuscleGroupIsBlank() {
        var request = """
                {
                  "name": "Squat",
                  "muscleGroup": "",
                  "equipment": "BARBELL",
                  "description": "Compound leg exercise"
                }
                """;

        var response = restClient.post()
                .uri("/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequestWhenJsonIsInvalid() {
        var request = """
                {
                  "name":
                }
                """;

        var response = restClient.post()
                .uri("/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }
}
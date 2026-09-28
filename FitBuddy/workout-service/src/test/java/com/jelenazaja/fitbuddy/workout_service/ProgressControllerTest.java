package com.jelenazaja.fitbuddy.workout_service;

import com.jelenazaja.fitbuddy.workout_service.progress.ProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProgressControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private ProgressService progressService;

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
    void shouldReturnExerciseProgress() {
        when(progressService.findProgressByExerciseId(1L))
                .thenReturn(null);

        var response = restClient.get()
                .uri("/progress/exercises/1")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnMaximumWeight() {
        when(progressService.findMaxWeightByExerciseId(1L))
                .thenReturn(null);

        var response = restClient.get()
                .uri("/progress/exercises/1/max-weight")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnExerciseVolume() {
        when(progressService.findVolumeByExerciseId(1L))
                .thenReturn(null);

        var response = restClient.get()
                .uri("/progress/exercises/1/volume")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnProgressSummary() {
        when(progressService.getSummary())
                .thenReturn(null);

        var response = restClient.get()
                .uri("/progress/summary")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }
}

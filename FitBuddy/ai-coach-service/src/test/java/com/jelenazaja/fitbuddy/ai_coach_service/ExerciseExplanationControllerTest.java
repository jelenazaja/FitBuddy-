package com.jelenazaja.fitbuddy.ai_coach_service;

import com.jelenazaja.fitbuddy.ai_coach_service.exercise.ExerciseExplanationService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExerciseExplanationControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private ExerciseExplanationService service;

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
    void shouldExplainExercise() {
        when(service.explain(1L))
                .thenReturn(null);

        var request = """
                {
                  "exerciseId": 1
                }
                """;

        var response = restClient.post()
                .uri("/ai/exercises/explain")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void shouldReturnBadRequestWhenExerciseIdIsMissing() {
        var request = """
                {}
                """;

        var response = restClient.post()
                .uri("/ai/exercises/explain")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequestWhenExerciseIdIsNegative() {
        var request = """
                {
                  "exerciseId": -1
                }
                """;

        var response = restClient.post()
                .uri("/ai/exercises/explain")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnTooManyRequestsWhenRateLimitIsExceeded() {
        var rateLimiter = RateLimiter.ofDefaults("openai");

        when(service.explain(1L))
                .thenThrow(
                        RequestNotPermitted.createRequestNotPermitted(rateLimiter)
                );

        var request = """
                {
                  "exerciseId": 1
                }
                """;

        var response = restClient.post()
                .uri("/ai/exercises/explain")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(429);
    }
}
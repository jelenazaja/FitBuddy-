package com.jelenazaja.fitbuddy.ai_coach_service;

import com.jelenazaja.fitbuddy.ai_coach_service.plan.GeneratedTrainingPlan;
import com.jelenazaja.fitbuddy.ai_coach_service.plan.GeneratedTrainingPlanService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainingPlanGenerationControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private GeneratedTrainingPlanService service;

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
    void shouldGenerateTrainingPlan() {
        when(service.generatePlan(any()))
                .thenReturn(null);

        var request = """
                {
                  "goal": "muscle gain",
                  "daysPerWeek": 4
                }
                """;

        var response = restClient.post()
                .uri("/ai/training-plans/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnBadRequestWhenGoalIsBlank() {
        var request = """
                {
                  "goal": "",
                }
                """;

        var response = restClient.post()
                .uri("/ai/training-plans/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnTooManyRequestsWhenRateLimitIsExceeded() {
        var rateLimiter = RateLimiter.ofDefaults("openai");

        when(service.generatePlan(any()))
                .thenThrow(
                        RequestNotPermitted.createRequestNotPermitted(rateLimiter)
                );

        var request = """
                {   
                  "goal": "muscle gain"
                }
                """;

        var response = restClient.post()
                .uri("/ai/training-plans/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(429);
    }
}
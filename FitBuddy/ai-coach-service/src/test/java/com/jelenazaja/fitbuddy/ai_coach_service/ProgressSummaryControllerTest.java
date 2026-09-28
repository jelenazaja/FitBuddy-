package com.jelenazaja.fitbuddy.ai_coach_service;

import com.jelenazaja.fitbuddy.ai_coach_service.progress.ProgressSummaryService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProgressSummaryControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @MockitoBean
    private ProgressSummaryService service;

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
    void shouldReturnProgressSummary() {
        when(service.generateSummary(1L))
                .thenReturn(null);

        var response = restClient.get()
                .uri("/ai/progress/summary/1")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturnTooManyRequestsWhenRateLimitIsExceeded() {
        var rateLimiter = RateLimiter.ofDefaults("openai");

        when(service.generateSummary(1L))
                .thenThrow(
                        RequestNotPermitted.createRequestNotPermitted(rateLimiter)
                );

        var response = restClient.get()
                .uri("/ai/progress/summary/1")
                .retrieve()
                .toEntity(String.class);

        then(response.getStatusCode().value()).isEqualTo(429);
    }
}

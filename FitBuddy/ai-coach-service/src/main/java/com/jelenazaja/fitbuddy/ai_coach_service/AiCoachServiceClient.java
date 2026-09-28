package com.jelenazaja.fitbuddy.ai_coach_service;

import com.jelenazaja.fitbuddy.ai_coach_service.exception.MissingIdExerciseException;
import com.jelenazaja.fitbuddy.ai_coach_service.exercise.ExerciseResponse;
import com.jelenazaja.fitbuddy.ai_coach_service.plan.ExercisesResponse;
import com.jelenazaja.fitbuddy.ai_coach_service.plan.TrainingPlanExerciseRequest;
import com.jelenazaja.fitbuddy.ai_coach_service.plan.TrainingPlanRequest;
import com.jelenazaja.fitbuddy.ai_coach_service.plan.TrainingPlanResponse;
import com.jelenazaja.fitbuddy.ai_coach_service.progress.ProgressSummaryResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class AiCoachServiceClient {

    private final RestClient restClient;

    public AiCoachServiceClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://workout-service:8080")
                .build();
    }

    public ExerciseResponse getExercise(Long exerciseId) {
        return restClient.get()
                .uri("/exercises/{id}", exerciseId)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new MissingIdExerciseException(exerciseId);
                        }
                )
                .body(ExerciseResponse.class);
    }

    public List<ExerciseResponse> getExercises() {

        ExercisesResponse response =
                restClient.get()
                        .uri("/exercises")
                        .retrieve()
                        .body(ExercisesResponse.class);

        return response.exercises();
    }

    public TrainingPlanResponse createTrainingPlan(
            TrainingPlanRequest request
    ) {
        return restClient.post()
                .uri("/training-plans")
                .body(request)
                .retrieve()
                .body(TrainingPlanResponse.class);
    }

    public void addExerciseToTrainingPlan(
            Long planId,
            TrainingPlanExerciseRequest request
    ) {
        restClient.post()
                .uri("/training-plans/{planId}/exercises", planId)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    public ProgressSummaryResponse getProgressSummary() {
        return restClient.get()
                .uri("/progress/summary")
                .retrieve()
                .body(ProgressSummaryResponse.class);
    }

    public ProgressSummaryResponse getProgressSummary(Long exerciseId) {
        return restClient.get()
                .uri("/progress/exercises/{exerciseId}/summary", exerciseId)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new MissingIdExerciseException(exerciseId);
                        }
                )
                .body(ProgressSummaryResponse.class);
    }

}

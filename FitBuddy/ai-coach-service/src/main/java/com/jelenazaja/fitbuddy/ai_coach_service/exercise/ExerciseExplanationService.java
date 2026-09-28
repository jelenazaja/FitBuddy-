package com.jelenazaja.fitbuddy.ai_coach_service.exercise;

import com.jelenazaja.fitbuddy.ai_coach_service.AiCoachServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ExerciseExplanationService {

    private final AiCoachServiceClient aiCoachServiceClient;
    private final CachedExerciseExplanationService cachedExplanationService;

    public ExerciseExplanationService(
            AiCoachServiceClient aiCoachServiceClient,
            CachedExerciseExplanationService cachedExplanationService
    ) {
        this.aiCoachServiceClient = aiCoachServiceClient;
        this.cachedExplanationService = cachedExplanationService;
    }

    public ExerciseExplanationResponse explain(Long exerciseId) {
            ExerciseResponse exercise =
                    aiCoachServiceClient.getExercise(exerciseId);

            return cachedExplanationService.getExplanation(exercise);

    }
}
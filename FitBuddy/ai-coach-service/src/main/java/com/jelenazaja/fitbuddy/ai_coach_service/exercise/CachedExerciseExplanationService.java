package com.jelenazaja.fitbuddy.ai_coach_service.exercise;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedExerciseExplanationService {

    private static final String FALLBACK_EXPLANATION = """
            Exercise explanation is currently unavailable.
            Please try again later.
            """;

    private final ChatClient chatClient;

    public CachedExerciseExplanationService(
            ChatClient.Builder chatClientBuilder
    ) {
        this.chatClient = chatClientBuilder.build();
    }

    @RateLimiter(name = "openai")
    @Cacheable(
            value = "exercise-explanations",
            key = "#exercise.id()"
    )
    public ExerciseExplanationResponse getExplanation(
            ExerciseResponse exercise
    ) {

        String explanation;

        try {
            explanation = chatClient.prompt()
                    .system("""
                            You are a fitness coach.
                            Explain exercises clearly and concisely.

                            Include:
                            - primary muscles worked
                            - correct technique
                            - common mistakes
                            - one safety tip

                            Base your answer only on the exercise information provided.
                            """)
                    .user("""
                            Explain this exercise:

                            Name: %s
                            Muscle group: %s
                            Equipment: %s
                            Description: %s
                            """.formatted(
                            exercise.name(),
                            exercise.muscleGroup(),
                            exercise.equipment(),
                            exercise.description()
                    ))
                    .call()
                    .content();

        } catch (Exception e) {
            explanation = FALLBACK_EXPLANATION;
        }

        return new ExerciseExplanationResponse(
                exercise.id(),
                exercise.name(),
                explanation
        );
    }
}

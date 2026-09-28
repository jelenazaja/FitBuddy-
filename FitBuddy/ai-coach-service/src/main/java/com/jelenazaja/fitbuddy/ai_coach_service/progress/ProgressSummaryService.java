package com.jelenazaja.fitbuddy.ai_coach_service.progress;

import com.jelenazaja.fitbuddy.ai_coach_service.AiCoachServiceClient;
import com.jelenazaja.fitbuddy.ai_coach_service.exercise.ExerciseResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ProgressSummaryService {

    private static final AiProgressSummary FALLBACK_SUMMARY =
            new AiProgressSummary(
                    "Progress summary is currently unavailable. Please try again later.",
                    "---",
                    "---",
                    "---"
            );

    private final AiCoachServiceClient aiCoachServiceClient;
    private final ChatClient chatClient;

    public ProgressSummaryService(
            AiCoachServiceClient aiCoachServiceClient,
            ChatClient.Builder chatClientBuilder
    ) {
        this.aiCoachServiceClient = aiCoachServiceClient;
        this.chatClient = chatClientBuilder.build();
    }

    @RateLimiter(name = "openai")
    public AiProgressSummary generateSummary() {
        ProgressSummaryResponse progress =
                aiCoachServiceClient.getProgressSummary();

        return buildSummary(progress, null);
    }

    @RateLimiter(name = "openai")
    public AiProgressSummary generateSummary(Long exerciseId) {
        ExerciseResponse exercise =
                aiCoachServiceClient.getExercise(exerciseId);

        ProgressSummaryResponse progress =
                aiCoachServiceClient.getProgressSummary(exerciseId);

        return buildSummary(progress, exercise.name());
    }

    private AiProgressSummary buildSummary(
            ProgressSummaryResponse progress,
            String exerciseName
    ) {
        try {
            return chatClient.prompt()
                    .system("""
                            You are a fitness coach.

                            Analyze the user's workout progress.

                            Rules:
                            - base your analysis only on the provided data
                            - do not invent workout statistics
                            - keep the advice realistic
                            - provide a short progress summary
                            - identify strengths
                            - identify possible improvements
                            - recommend the next workout
                            """)
                    .user("""
                            User progress data%s:

                            Total workouts: %d
                            Total sets: %d
                            Total volume: %.2f
                            Maximum weight lifted: %.2f
                            """.formatted(
                            exerciseName == null
                                    ? ""
                                    : " for %s".formatted(exerciseName),
                            progress.totalWorkouts(),
                            progress.totalSets(),
                            progress.totalVolume(),
                            progress.maxWeight()
                    ))
                    .call()
                    .entity(AiProgressSummary.class);

        } catch (Exception e) {
            return FALLBACK_SUMMARY;
        }
    }
}
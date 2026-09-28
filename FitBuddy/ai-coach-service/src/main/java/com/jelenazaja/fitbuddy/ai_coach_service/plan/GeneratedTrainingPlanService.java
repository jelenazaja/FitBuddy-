package com.jelenazaja.fitbuddy.ai_coach_service.plan;

import com.jelenazaja.fitbuddy.ai_coach_service.AiCoachServiceClient;
import com.jelenazaja.fitbuddy.ai_coach_service.exercise.ExerciseResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedTrainingPlanService {

    private final AiCoachServiceClient aiCoachServiceClient;
    private final ChatClient chatClient;

    private static final GeneratedTrainingPlan FALLBACK_PLAN =
            new GeneratedTrainingPlan(
                    "Training plan currently unavailable",
                    List.of()
            );

    public GeneratedTrainingPlanService(
            AiCoachServiceClient aiCoachServiceClient,
            ChatClient.Builder chatClientBuilder
    ) {
        this.aiCoachServiceClient = aiCoachServiceClient;
        this.chatClient = chatClientBuilder.build();
    }

    @RateLimiter(name = "openai")
    public GeneratedTrainingPlan generatePlan(
            GenerateTrainingPlanRequest request
    ) {
        List<ExerciseResponse> exercises =
                aiCoachServiceClient.getExercises();

        GeneratedTrainingPlan generated;

        try {
            generated = chatClient.prompt()
                    .system("""
                        You are a fitness coach.

                        Generate one realistic training plan.

                        Rules:
                        - use only exercises from the provided exercise catalog
                        - never invent exercise IDs
                        - every exerciseId must exist in the provided catalog
                        - choose realistic sets and repetitions
                        - order exercises logically
                        """)
                    .user("""
                        User goal: %s
                        Experience level: %s

                        Available exercise catalog:
                        %s
                        """.formatted(
                            request.goal(),
                            request.experienceLevel(),
                            exercises
                    ))
                    .call()
                    .entity(GeneratedTrainingPlan.class);

        } catch (Exception e) {
            return FALLBACK_PLAN;
        }

        TrainingPlanResponse savedPlan =
                aiCoachServiceClient.createTrainingPlan(
                        new TrainingPlanRequest(generated.name())
                );

        for (GeneratedTrainingPlanExercise exercise : generated.exercises()) {

            TrainingPlanExerciseRequest exerciseRequest =
                    new TrainingPlanExerciseRequest(
                            exercise.exerciseId(),
                            exercise.orderIndex(),
                            exercise.targetSets(),
                            exercise.targetReps(),
                            exercise.targetWeight(),
                            exercise.notes()
                    );

            aiCoachServiceClient.addExerciseToTrainingPlan(
                    savedPlan.id(),
                    exerciseRequest
            );
        }

        return generated;
    }
}
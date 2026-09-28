package com.jelenazaja.fitbuddy.workout_service.plan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TrainingPlanExercise(
        Long id,
        Long trainingPlanId,
        @NotNull
        Long exerciseId,
        @Min(1)
        int orderIndex,
        @Min(1)
        int targetSets,
        @Min(1)
        int targetReps,
        @Min(0)
        double targetWeight,
        String notes
) {}
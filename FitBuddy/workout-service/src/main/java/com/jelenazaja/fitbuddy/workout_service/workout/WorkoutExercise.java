package com.jelenazaja.fitbuddy.workout_service.workout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WorkoutExercise(
        Long id,
        Long workoutId,
        @NotNull Long exerciseId,
        @NotNull @Min(0) int orderIndex,
        String notes
) {}
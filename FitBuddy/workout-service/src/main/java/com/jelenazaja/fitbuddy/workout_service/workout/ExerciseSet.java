package com.jelenazaja.fitbuddy.workout_service.workout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ExerciseSet (
        Long id,
        Long workoutExerciseId,
        @Min(0) int setNumber,
        @Min(0) int reps,
        @Min(0) double weight
) {
}

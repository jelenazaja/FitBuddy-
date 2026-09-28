package com.jelenazaja.fitbuddy.workout_service.exercise.api;

import jakarta.validation.constraints.NotBlank;

public record NewExerciseRequest(
        @NotBlank String name,
        @NotBlank String muscleGroup,
        String equipment,
        String description
) {}
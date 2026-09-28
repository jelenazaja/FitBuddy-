package com.jelenazaja.fitbuddy.workout_service.exercise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Exercise(
        Long id,
        @NotBlank String name,
        @NotBlank String muscleGroup,
        @NotBlank String equipment,
        @NotBlank String description
) {}
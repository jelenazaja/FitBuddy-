package com.jelenazaja.fitbuddy.workout_service.plan;

import jakarta.validation.constraints.NotBlank;

public record TrainingPlan(
        Long id,
        @NotBlank
        String name
) {}
package com.jelenazaja.fitbuddy.workout_service.workout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDate;

public record Workout(
        Long id,
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull @Min(0) Long durationMinutes,
        String note
) {}
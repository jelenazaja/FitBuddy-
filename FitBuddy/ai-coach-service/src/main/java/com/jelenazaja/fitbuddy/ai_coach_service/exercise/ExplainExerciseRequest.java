package com.jelenazaja.fitbuddy.ai_coach_service.exercise;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ExplainExerciseRequest(
        @NotNull @Min(0) Long exerciseId
) {
}

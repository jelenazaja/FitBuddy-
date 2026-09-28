package com.jelenazaja.fitbuddy.ai_coach_service.plan;

import jakarta.validation.constraints.NotBlank;

public record TrainingPlanRequest(
       @NotBlank String name
) {
}

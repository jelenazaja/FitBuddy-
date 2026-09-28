package com.jelenazaja.fitbuddy.ai_coach_service.plan;

public record GenerateTrainingPlanRequest(
        String goal,
        String experienceLevel
) {
}

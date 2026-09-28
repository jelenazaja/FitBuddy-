package com.jelenazaja.fitbuddy.ai_coach_service.progress;

public record AiProgressSummary(
        String summary,
        String strengths,
        String improvements,
        String nextWorkoutRecommendation
) {
}

package com.jelenazaja.fitbuddy.ai_coach_service.progress;

public record ProgressSummaryResponse(
        Long totalWorkouts,
        Long totalSets,
        Double totalVolume,
        Double maxWeight
) {
}

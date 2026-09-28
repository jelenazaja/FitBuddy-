package com.jelenazaja.fitbuddy.workout_service.progress.records;

public record ProgressSummaryResponse(
        Long exerciseId,
        //String exerciseName,
        long totalWorkouts,
        long totalSets,
        double maxWorkoutVolume,
        double maxWeight
) {
}

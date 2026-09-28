package com.jelenazaja.fitbuddy.workout_service.progress.records;

public record ProgressSummary(
        long totalWorkouts,
        long totalSets,
        double totalVolume,
        double maxWeight
) {}

package com.jelenazaja.fitbuddy.ai_coach_service.plan;

public record TrainingPlanExerciseRequest(
        Long exerciseId,
        Integer orderIndex,
        Integer targetSets,
        Integer targetReps,
        Double targetWeight,
        String notes
) {
}

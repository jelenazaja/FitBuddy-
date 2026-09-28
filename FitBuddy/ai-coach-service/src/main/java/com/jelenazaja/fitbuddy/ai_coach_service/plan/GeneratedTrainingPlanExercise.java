package com.jelenazaja.fitbuddy.ai_coach_service.plan;

public record GeneratedTrainingPlanExercise(
        Long exerciseId,
        Integer orderIndex,
        Integer targetSets,
        Integer targetReps,
        Double targetWeight,
        String notes
) {
}

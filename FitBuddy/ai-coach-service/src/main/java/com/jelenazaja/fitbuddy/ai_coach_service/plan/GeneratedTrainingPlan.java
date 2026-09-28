package com.jelenazaja.fitbuddy.ai_coach_service.plan;

import java.util.List;

public record GeneratedTrainingPlan(
        String name,
        List<GeneratedTrainingPlanExercise> exercises
) {
}

package com.jelenazaja.fitbuddy.ai_coach_service.exercise;
import java.io.Serializable;

public record ExerciseExplanationResponse(
        Long exerciseId,
        String exerciseName,
        String explanation
) implements Serializable {
}

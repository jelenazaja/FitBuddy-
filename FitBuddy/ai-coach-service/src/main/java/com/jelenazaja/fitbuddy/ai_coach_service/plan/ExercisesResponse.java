package com.jelenazaja.fitbuddy.ai_coach_service.plan;

import com.jelenazaja.fitbuddy.ai_coach_service.exercise.ExerciseResponse;

import java.util.List;

public record ExercisesResponse(
        List<ExerciseResponse> exercises
) {
}

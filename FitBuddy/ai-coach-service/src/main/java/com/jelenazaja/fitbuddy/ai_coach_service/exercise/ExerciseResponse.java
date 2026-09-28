package com.jelenazaja.fitbuddy.ai_coach_service.exercise;

public record ExerciseResponse (
        Long id,
        String name,
        String muscleGroup,
        String equipment,
        String description
){}

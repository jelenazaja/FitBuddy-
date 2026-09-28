package com.jelenazaja.fitbuddy.workout_service.progress.records;

import java.time.LocalDate;

public record ExerciseProgressEntry(
        Long workoutId,
        String workoutName,
        LocalDate date,
        int setNumber,
        int reps,
        double weight
) {}
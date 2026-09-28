package com.jelenazaja.fitbuddy.workout_service.progress.records;

import java.time.LocalDate;

public record ExerciseVolumeEntry(
        Long workoutId,
        String workoutName,
        LocalDate date,
        double volume
) {}

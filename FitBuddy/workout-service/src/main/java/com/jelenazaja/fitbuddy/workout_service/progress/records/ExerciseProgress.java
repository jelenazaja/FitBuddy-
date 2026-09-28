package com.jelenazaja.fitbuddy.workout_service.progress.records;

import java.util.List;

public record ExerciseProgress(
        Long exerciseId,
        List<ExerciseProgressEntry> entries
) {}

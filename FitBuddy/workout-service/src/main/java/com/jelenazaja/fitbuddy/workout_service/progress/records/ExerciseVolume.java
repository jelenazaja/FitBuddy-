package com.jelenazaja.fitbuddy.workout_service.progress.records;

import java.util.List;

public record ExerciseVolume(
        Long exerciseId,
        List<ExerciseVolumeEntry> volumes
) {}

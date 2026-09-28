package com.jelenazaja.fitbuddy.workout_service.workout.exception;

public class MissingIdWorkoutExerciseException extends RuntimeException {

    public MissingIdWorkoutExerciseException(Long id) {
        super("Workout exercise not found with id: " + id);
    }
}

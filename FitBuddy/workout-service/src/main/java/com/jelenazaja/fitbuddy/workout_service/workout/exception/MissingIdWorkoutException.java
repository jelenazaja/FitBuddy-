package com.jelenazaja.fitbuddy.workout_service.workout.exception;

public class MissingIdWorkoutException extends RuntimeException {

    public MissingIdWorkoutException(Long id) {
        super("Workout not found with id: " + id);
    }
}

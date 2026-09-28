package com.jelenazaja.fitbuddy.workout_service.workout.exception;

public class MissingNameWorkoutException extends RuntimeException {
    public MissingNameWorkoutException(String name) {
        super("Workout not found with name: " + name);
    }
}

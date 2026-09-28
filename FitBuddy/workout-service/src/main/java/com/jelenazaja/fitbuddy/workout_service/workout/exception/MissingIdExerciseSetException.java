package com.jelenazaja.fitbuddy.workout_service.workout.exception;

public class MissingIdExerciseSetException extends RuntimeException {

    public MissingIdExerciseSetException(Long id) {
        super("Exercise set not found with id: " + id);
    }
}

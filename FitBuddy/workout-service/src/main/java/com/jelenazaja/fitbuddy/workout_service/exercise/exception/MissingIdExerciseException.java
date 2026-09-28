package com.jelenazaja.fitbuddy.workout_service.exercise.exception;

public class MissingIdExerciseException extends RuntimeException{
    public MissingIdExerciseException(Long id) {
        super("Missing id exercise " + id);
    }
}

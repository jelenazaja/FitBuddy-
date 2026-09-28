package com.jelenazaja.fitbuddy.workout_service.exercise.exception;

public class ExerciseAlreadyExistsException extends RuntimeException {

    public ExerciseAlreadyExistsException(String name) {
        super("Exercise with name " + name + " already exists.");
    }
}
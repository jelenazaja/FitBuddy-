package com.jelenazaja.fitbuddy.workout_service.exercise.exception;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(String name) {
        super("Exercise with name " + name + " was not found.");
    }
}
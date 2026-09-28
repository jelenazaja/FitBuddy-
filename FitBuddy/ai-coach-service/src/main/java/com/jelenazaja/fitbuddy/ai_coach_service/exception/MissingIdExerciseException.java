package com.jelenazaja.fitbuddy.ai_coach_service.exception;

public class MissingIdExerciseException extends RuntimeException {

    public MissingIdExerciseException(Long id) {
        super("Exercise with id " + id + " does not exist.");
    }
}

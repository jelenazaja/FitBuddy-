package com.jelenazaja.fitbuddy.workout_service.plan.exception;

public class MissingIdTrainingPlanException extends RuntimeException {

    public MissingIdTrainingPlanException(Long id) {
        super("Training plan with id " + id + " does not exist.");
    }
}

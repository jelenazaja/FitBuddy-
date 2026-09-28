package com.jelenazaja.fitbuddy.workout_service.plan.exception;

public class MissingIdTrainingPlanExerciseException extends RuntimeException {

    public MissingIdTrainingPlanExerciseException(Long id) {
        super("Training plan exercise with id " + id + " does not exist.");
    }
}

package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise;

import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlan;
import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlanExercise;
import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutExercise;

import java.util.List;
import java.util.Optional;

public interface TrainingPlanExerciseRepository {

    void deleteById(Long id);
    boolean existsById(Long id);
    TrainingPlanExercise save(Long planId,TrainingPlanExercise trainingPlanExercise);
    List<TrainingPlanExercise> findByTrainingPlanIdOrderByOrderIndex(Long trainingPlanId);
}

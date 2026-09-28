package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan;

import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlan;

import java.util.Collection;
import java.util.Optional;

public interface TrainingPlanRepository {

    Optional<TrainingPlan> findById(Long id);
    void deleteById(Long id);
    TrainingPlan save(TrainingPlan trainingPlan);
    Collection<TrainingPlan> findAll();
    boolean existsById(Long id);
}

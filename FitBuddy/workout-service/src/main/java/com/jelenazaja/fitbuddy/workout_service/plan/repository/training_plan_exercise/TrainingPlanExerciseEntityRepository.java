package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingPlanExerciseEntityRepository extends JpaRepository<TrainingPlanExerciseEntity, Long> {

    List<TrainingPlanExerciseEntity> findByTrainingPlanIdOrderByOrderIndex(Long trainingPlanId);

    void deleteByExerciseId(Long exerciseId);
}

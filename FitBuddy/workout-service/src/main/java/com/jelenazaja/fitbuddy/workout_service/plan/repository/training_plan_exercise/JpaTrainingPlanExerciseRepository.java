package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise;

import com.jelenazaja.fitbuddy.workout_service.exercise.exception.MissingIdExerciseException;
import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseEntityRepository;
import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlanExercise;
import com.jelenazaja.fitbuddy.workout_service.plan.exception.MissingIdTrainingPlanException;
import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan.TrainingPlanEntityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JpaTrainingPlanExerciseRepository implements TrainingPlanExerciseRepository {

    private final TrainingPlanExerciseEntityRepository trainingPlanExerciseEntityRepository;
    private final TrainingPlanEntityRepository trainingPlanEntityRepository;
    private final ExerciseEntityRepository exerciseEntityRepository;

    public JpaTrainingPlanExerciseRepository(TrainingPlanExerciseEntityRepository trainingPlanExerciseEntityRepository,
                                             TrainingPlanEntityRepository trainingPlanEntityRepository, ExerciseEntityRepository exerciseEntityRepository) {
        this.trainingPlanExerciseEntityRepository = trainingPlanExerciseEntityRepository;
        this.trainingPlanEntityRepository = trainingPlanEntityRepository;
        this.exerciseEntityRepository = exerciseEntityRepository;

    }

    @Override
    public List<TrainingPlanExercise> findByTrainingPlanIdOrderByOrderIndex(Long trainingPlanId) {
        return trainingPlanExerciseEntityRepository.findByTrainingPlanIdOrderByOrderIndex(trainingPlanId)
                .stream()
                .map(TrainingPlanExerciseEntity::toTrainingPlanExercise)
                .toList();
    }

    @Override
    public boolean existsById(Long trainingPlanId) {
        return trainingPlanExerciseEntityRepository.existsById(trainingPlanId);
    }

    @Override
    public void  deleteById(Long id) {
        trainingPlanExerciseEntityRepository.deleteById(id);
    }

    @Transactional
    @Override
    public TrainingPlanExercise save(Long planId, TrainingPlanExercise trainingPlanExercise) {
        var trainingPlanEntity = trainingPlanEntityRepository.findById(planId)
                .orElseThrow(() -> new MissingIdTrainingPlanException(planId));

        var exerciseEntity = exerciseEntityRepository.findById(trainingPlanExercise.exerciseId())
                .orElseThrow(() -> new MissingIdExerciseException(trainingPlanExercise.exerciseId()));

        var entity = TrainingPlanExerciseEntity.from(trainingPlanExercise);

        entity.setTrainingPlan(trainingPlanEntity);
        entity.setExercise(exerciseEntity);

        var savedEntity = trainingPlanExerciseEntityRepository.save(entity);
        return savedEntity.toTrainingPlanExercise();
    }

}

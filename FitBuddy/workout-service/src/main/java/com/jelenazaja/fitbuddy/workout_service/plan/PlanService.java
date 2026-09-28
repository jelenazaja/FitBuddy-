package com.jelenazaja.fitbuddy.workout_service.plan;

import com.jelenazaja.fitbuddy.workout_service.exercise.exception.MissingIdExerciseException;
import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseEntityRepository;
import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseRepository;
import com.jelenazaja.fitbuddy.workout_service.plan.exception.MissingIdTrainingPlanException;
import com.jelenazaja.fitbuddy.workout_service.plan.exception.MissingIdTrainingPlanExerciseException;

import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan.TrainingPlanRepository;
import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise.TrainingPlanExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingPlanExerciseRepository trainingPlanExerciseRepository;

    public PlanService(
            TrainingPlanRepository trainingPlanRepository,
            TrainingPlanExerciseRepository trainingPlanExerciseRepository
    ) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingPlanExerciseRepository = trainingPlanExerciseRepository;
    }

    //GET /training-plans
    public Collection<TrainingPlan> findAll() {
        return trainingPlanRepository.findAll();
    }

    //GET /training-plans/{id}
    public TrainingPlan findById(Long id) {
        return trainingPlanRepository.findById(id)
                .orElseThrow(() -> new MissingIdTrainingPlanException(id));
    }

    //POST /training-plans
    public TrainingPlan save(TrainingPlan trainingPlan) {
        return trainingPlanRepository.save(trainingPlan);

    }

    //DELETE /training-plans/{id}
    public void deleteById(Long id) {
        if (!trainingPlanRepository.existsById(id)) {
            throw new MissingIdTrainingPlanException(id);
        }

        trainingPlanRepository.deleteById(id);
    }

    //GET /training-plans/{planId}/exercises
    public Collection<TrainingPlanExercise> findExercisesByPlanId(Long planId) {
        if (!trainingPlanRepository.existsById(planId)) {
            throw new MissingIdTrainingPlanException(planId);
        }

        return trainingPlanExerciseRepository.findByTrainingPlanIdOrderByOrderIndex(planId);
    }

    //POST /training-plans/{planId}/exercises
    public TrainingPlanExercise addExerciseToPlan(Long planId, TrainingPlanExercise trainingPlanExercise) {
        if (!trainingPlanRepository.existsById(planId)) {
            throw new MissingIdTrainingPlanException(planId);
        }
        return trainingPlanExerciseRepository.save(planId,trainingPlanExercise);
    }

    //DELETE /training-plan-exercises/{id}
    public void deleteTrainingPlanExercise(Long id) {
        if (!trainingPlanExerciseRepository.existsById(id)) {
            throw new MissingIdTrainingPlanExerciseException(id);
        }

        trainingPlanExerciseRepository.deleteById(id);
    }
}

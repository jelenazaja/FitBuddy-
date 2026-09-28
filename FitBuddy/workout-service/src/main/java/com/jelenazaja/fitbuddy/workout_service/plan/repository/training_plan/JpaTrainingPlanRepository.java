package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan;

import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlan;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaTrainingPlanRepository implements TrainingPlanRepository {

    private final TrainingPlanEntityRepository trainingPlanEntityRepository;

    public JpaTrainingPlanRepository(TrainingPlanEntityRepository trainingPlanEntityRepository) {
        this.trainingPlanEntityRepository = trainingPlanEntityRepository;
    }

    @Override
    public TrainingPlan save(TrainingPlan trainingPlan) {
        return trainingPlanEntityRepository.save(TrainingPlanEntity.from(trainingPlan)).toTrainingPlan();
    }

    @Override
    public void deleteById(Long id) {
        trainingPlanEntityRepository.deleteById(id);
    }

    @Override
    public Optional<TrainingPlan> findById(Long id) {
        return trainingPlanEntityRepository.findById(id).map(TrainingPlanEntity::toTrainingPlan);
    }

    @Override
    public Collection<TrainingPlan> findAll() {
        return trainingPlanEntityRepository.findAll()
                .stream()
                .map(TrainingPlanEntity::toTrainingPlan)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return trainingPlanEntityRepository.existsById(id);
    }


}

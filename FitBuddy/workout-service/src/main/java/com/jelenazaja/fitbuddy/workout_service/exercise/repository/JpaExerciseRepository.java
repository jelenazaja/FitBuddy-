package com.jelenazaja.fitbuddy.workout_service.exercise.repository;

import com.jelenazaja.fitbuddy.workout_service.exercise.Exercise;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise.TrainingPlanExerciseEntityRepository;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise.WorkoutExerciseEntityRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaExerciseRepository implements ExerciseRepository {

    private final ExerciseEntityRepository exerciseEntityRepository;


    public JpaExerciseRepository(ExerciseEntityRepository exerciseEntityRepository) {
        this.exerciseEntityRepository = exerciseEntityRepository;
    }

    @Override
    public Collection<Exercise> findAll() {
        return exerciseEntityRepository.findAll()
                .stream()
                .map(ExerciseEntity::toExercise)
                .toList();
    }

    @Override
    public Optional<Exercise> findByName(String name) {
        return exerciseEntityRepository.findByName(name)
                .map(ExerciseEntity::toExercise);
    }

    @Override
    public Exercise save(Exercise exercise) {
        return exerciseEntityRepository.save(ExerciseEntity.from(exercise))
                .toExercise();
    }

    @Override
    public void deleteByName(String name) {
        exerciseEntityRepository.deleteByName(name);
    }

    @Override
    public Collection<Exercise> findByMuscleGroup(String muscleGroup) {
        return exerciseEntityRepository.findByMuscleGroup(muscleGroup)
                .stream()
                .map(ExerciseEntity::toExercise)
                .toList();
    }

    @Override
    public Optional<Exercise> findById(Long id) {
        return exerciseEntityRepository.findById(id)
                .map(ExerciseEntity::toExercise);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        exerciseEntityRepository.deleteById(id);
    }
}
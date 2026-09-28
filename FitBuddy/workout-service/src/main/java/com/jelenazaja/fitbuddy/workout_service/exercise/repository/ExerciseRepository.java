package com.jelenazaja.fitbuddy.workout_service.exercise.repository;

import com.jelenazaja.fitbuddy.workout_service.exercise.Exercise;

import java.util.Collection;
import java.util.Optional;

public interface ExerciseRepository {

    Collection<Exercise> findAll();
    Optional<Exercise> findByName(String name);
    Collection<Exercise> findByMuscleGroup(String muscleGroup);
    Exercise save(Exercise exercise);
    Optional<Exercise> findById(Long id);
    void deleteByName(String name);
    void deleteById(Long id);

}
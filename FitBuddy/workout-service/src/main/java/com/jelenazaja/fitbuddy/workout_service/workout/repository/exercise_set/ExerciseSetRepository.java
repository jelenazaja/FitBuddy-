package com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set;

import com.jelenazaja.fitbuddy.workout_service.workout.ExerciseSet;

import java.util.Collection;
import java.util.Optional;

public interface ExerciseSetRepository {

    Collection<ExerciseSet> findByWorkoutExerciseId(Long workoutExerciseId);
    Optional<ExerciseSet> findById(Long id);
    ExerciseSet save(Long workoutExerciseId, ExerciseSet exerciseSet);
    void deleteByWorkoutExerciseId(Long workoutExerciseId);
    void deleteById(Long id);
    boolean existsById(Long id);
}
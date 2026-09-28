package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise;

import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutExercise;

import java.util.List;
import java.util.Optional;

public interface WorkoutExerciseRepository {

    WorkoutExercise save(Long workoutId, WorkoutExercise workoutExercise);
    Optional<WorkoutExercise> findById(Long id);
    List<WorkoutExercise> findByWorkoutId(Long workoutId);
    boolean existsById(Long id);
    void deleteById(Long id);
}

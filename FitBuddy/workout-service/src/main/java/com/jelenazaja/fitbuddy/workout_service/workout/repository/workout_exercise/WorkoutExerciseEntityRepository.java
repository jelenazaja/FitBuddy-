package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExerciseEntityRepository extends JpaRepository<WorkoutExerciseEntity, Long> {

    List<WorkoutExerciseEntity> findByWorkoutId(Long workoutId);
    boolean existsById(Long id);

    void deleteByExerciseId(Long exerciseId);

}
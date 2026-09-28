package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout;

import com.jelenazaja.fitbuddy.workout_service.workout.Workout;

import java.util.Collection;
import java.util.Optional;

public interface WorkoutRepository {

    Collection<Workout> findAll();
    Optional<Workout> findById(Long id);
    Optional<Workout> findByName(String name);
    Workout save(Workout workout);
    void deleteById(Long id);
    boolean existsById(Long id);
}

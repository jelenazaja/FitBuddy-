package com.jelenazaja.fitbuddy.workout_service.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ExerciseEntityRepository extends JpaRepository<ExerciseEntity, Long> {

    Collection<ExerciseEntity> findByMuscleGroup(String muscleGroup);
    Optional<ExerciseEntity> findByName(String name);
    void deleteByName(String name);

}
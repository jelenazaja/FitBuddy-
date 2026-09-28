package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutEntityRepository extends JpaRepository<WorkoutEntity, Long> {

    Optional<WorkoutEntity> findByName(String name);

}

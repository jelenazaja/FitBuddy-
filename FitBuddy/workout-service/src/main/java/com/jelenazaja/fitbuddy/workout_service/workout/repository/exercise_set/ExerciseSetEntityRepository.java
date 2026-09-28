package com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseSetEntityRepository extends JpaRepository<ExerciseSetEntity, Long> {

    List<ExerciseSetEntity> findByWorkoutExerciseIdOrderBySetNumberAsc(Long workoutExerciseId);
    void deleteByWorkoutExerciseId(Long workoutExerciseId);

}
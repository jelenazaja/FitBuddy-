package com.jelenazaja.fitbuddy.workout_service.progress.repository;

import com.jelenazaja.fitbuddy.workout_service.progress.records.ExerciseProgressEntry;
import com.jelenazaja.fitbuddy.workout_service.progress.records.ExerciseVolumeEntry;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set.ExerciseSetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgressRepository extends Repository<ExerciseSetEntity, Long> {

    @Query("""
            SELECT new com.jelenazaja.fitbuddy.workout_service.progress.records.ExerciseProgressEntry(
                w.id,
                w.name,
                w.date,
                es.setNumber,
                es.reps,
                es.weight
            )
            FROM ExerciseSetEntity es
            JOIN es.workoutExercise we
            JOIN we.workout w
            JOIN we.exercise e
            WHERE e.id = :exerciseId
            ORDER BY w.date ASC, es.setNumber ASC
            """)
    List<ExerciseProgressEntry> findProgressByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query("""
            SELECT COALESCE(MAX(es.weight), 0)
            FROM ExerciseSetEntity es
            JOIN es.workoutExercise we
            JOIN we.exercise e
            WHERE e.id = :exerciseId
            """)
    double findMaxWeightByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query("""
            SELECT new com.jelenazaja.fitbuddy.workout_service.progress.records.ExerciseVolumeEntry(
                w.id,
                w.name,
                w.date,
                SUM(es.reps * es.weight)
            )
            FROM ExerciseSetEntity es
            JOIN es.workoutExercise we
            JOIN we.workout w
            JOIN we.exercise e
            WHERE e.id = :exerciseId
            GROUP BY w.id, w.name, w.date
            ORDER BY w.date ASC
            """)
    List<ExerciseVolumeEntry> findVolumeByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query("""
        SELECT COUNT(w)
        FROM WorkoutEntity w
        """)
    long countWorkouts();

    @Query("""
        SELECT COUNT(es)
        FROM ExerciseSetEntity es
        """)
    long countSets();

    @Query("""
        SELECT COALESCE(SUM(es.reps * es.weight), 0)
        FROM ExerciseSetEntity es
        """)
    double sumTotalVolume();

    @Query("""
        SELECT COALESCE(MAX(es.weight), 0)
        FROM ExerciseSetEntity es
        """)
    double findMaxWeight();


}

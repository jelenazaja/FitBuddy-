package com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set;

import com.jelenazaja.fitbuddy.workout_service.workout.ExerciseSet;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise.WorkoutExerciseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "exercise_sets")
public class ExerciseSetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExerciseEntity workoutExercise;

    @Column(nullable = false)
    private int setNumber;

    @Column(nullable = false)
    private int reps;

    @Column(nullable = false)
    private double weight;

    protected ExerciseSetEntity() {}

    public static ExerciseSetEntity from(ExerciseSet exerciseSet) {
        var entity = new ExerciseSetEntity();
        entity.setNumber = exerciseSet.setNumber();
        entity.reps = exerciseSet.reps();
        entity.weight = exerciseSet.weight();
        return entity;
    }

    public ExerciseSet toExerciseSet() {
        return new ExerciseSet(
                id,
                workoutExercise.getId(),
                setNumber,
                reps,
                weight
        );
    }

    public void setWorkoutExercise(WorkoutExerciseEntity workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public Long getId() {
        return id;
    }

}

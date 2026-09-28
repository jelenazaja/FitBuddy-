package com.jelenazaja.fitbuddy.workout_service.exercise.repository;

import com.jelenazaja.fitbuddy.workout_service.exercise.Exercise;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise.WorkoutExerciseEntity;
import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise.TrainingPlanExerciseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "exercises")
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<WorkoutExerciseEntity> workoutExercises = new ArrayList<>();

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TrainingPlanExerciseEntity> trainingPlanExercises = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String muscleGroup;

    @Column(nullable = false)
    private String equipment;

    @Column(nullable = false, length = 1000)
    private String description;

    protected ExerciseEntity() {}

    public static ExerciseEntity from(Exercise exercise) {
        var entity = new ExerciseEntity();
        entity.name = exercise.name();
        entity.muscleGroup = exercise.muscleGroup();
        entity.equipment = exercise.equipment();
        entity.description = exercise.description();
        return entity;
    }

    public Exercise toExercise() {
        return new Exercise(
                id,
                name,
                muscleGroup,
                equipment,
                description
        );
    }

    public Long getId() {
        return id;
    }
}
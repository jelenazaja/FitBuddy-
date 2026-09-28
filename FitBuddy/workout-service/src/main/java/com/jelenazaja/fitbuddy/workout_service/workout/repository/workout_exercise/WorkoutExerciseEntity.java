package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise;

import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseEntity;
import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutExercise;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set.ExerciseSetEntity;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout.WorkoutEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_exercises")
public class WorkoutExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private WorkoutEntity workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

    @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSetEntity> sets = new ArrayList<>();

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    private String notes;

    protected WorkoutExerciseEntity() {}

    public static WorkoutExerciseEntity from(WorkoutExercise workoutExercise) {
        var entity = new WorkoutExerciseEntity();
        entity.orderIndex = workoutExercise.orderIndex();
        entity.notes = workoutExercise.notes();
        return entity;
    }

    public WorkoutExercise toWorkoutExercise() {
        return new WorkoutExercise(
                id,
                workout.getId(),
                exercise.getId(),
                orderIndex,
                notes
        );
    }

    public void addSet(ExerciseSetEntity setEntity) {
        setEntity.setWorkoutExercise(this);
        sets.add(setEntity);
    }

    public void setWorkout(WorkoutEntity workout) {
        this.workout = workout;
    }

    public void setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
    }

    public Long getId() {
        return id;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public String getNotes() {
        return notes;
    }
}
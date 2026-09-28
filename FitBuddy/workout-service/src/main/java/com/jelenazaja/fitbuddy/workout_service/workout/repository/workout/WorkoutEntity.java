package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout;

import com.jelenazaja.fitbuddy.workout_service.workout.Workout;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise.WorkoutExerciseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workouts")
public class WorkoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExerciseEntity> exercises = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long durationMinutes;

    @Column(length = 1000)
    private String note;

    protected WorkoutEntity() {}

    public static WorkoutEntity from(Workout workout) {
        var entity = new WorkoutEntity();
        entity.name = workout.name();
        entity.date = workout.date();
        entity.durationMinutes =  workout.durationMinutes();
        entity.note = workout.note();
        return entity;
    }

    public Workout toWorkout() {
        return new Workout(
                id,
                name,
                date,
                durationMinutes,
                note
        );
    }

    public void addExercise(WorkoutExerciseEntity workoutExercise) {
        exercises.add(workoutExercise);
        workoutExercise.setWorkout(this);
    }

    public Long getId() {
        return id;
    }
}
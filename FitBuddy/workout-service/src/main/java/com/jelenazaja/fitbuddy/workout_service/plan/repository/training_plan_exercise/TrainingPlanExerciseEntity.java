package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise;

import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseEntity;
import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlanExercise;
import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan.TrainingPlanEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "training_plan_exercises")
public class TrainingPlanExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlanEntity trainingPlan;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @Column(name = "target_sets", nullable = false)
    private int targetSets;

    @Column(name = "target_reps", nullable = false)
    private int targetReps;

    @Column(name = "target_weight", nullable = false)
    private double targetWeight;

    private String notes;

    protected TrainingPlanExerciseEntity() {}

    public static TrainingPlanExerciseEntity from(TrainingPlanExercise trainingPlanExercise) {
        var entity = new TrainingPlanExerciseEntity();
        entity.id = trainingPlanExercise.id();
        entity.orderIndex = trainingPlanExercise.orderIndex();
        entity.targetSets = trainingPlanExercise.targetSets();
        entity.targetReps = trainingPlanExercise.targetReps();
        entity.targetWeight = trainingPlanExercise.targetWeight();
        entity.notes = trainingPlanExercise.notes();
        return entity;
    }

    public TrainingPlanExercise toTrainingPlanExercise() {
        return new TrainingPlanExercise(
                id,
                trainingPlan.getId(),
                exercise.getId(),
                orderIndex,
                targetSets,
                targetReps,
                targetWeight,
                notes
        );
    }

    public void setTrainingPlan(TrainingPlanEntity trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public void setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
    }

    public Long getId() {
        return id;
    }
}

package com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan;

import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlan;
import com.jelenazaja.fitbuddy.workout_service.plan.repository.training_plan_exercise.TrainingPlanExerciseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_plans")
public class TrainingPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingPlanExerciseEntity> exercises = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    protected TrainingPlanEntity() {}

    public static TrainingPlanEntity from(TrainingPlan trainingPlan) {
        var entity = new TrainingPlanEntity();
        entity.id = trainingPlan.id();
        entity.name = trainingPlan.name();
        return entity;
    }

    public TrainingPlan toTrainingPlan() {
        return new TrainingPlan(
                id,
                name
        );
    }

    public Long getId() {
        return id;
    }
}

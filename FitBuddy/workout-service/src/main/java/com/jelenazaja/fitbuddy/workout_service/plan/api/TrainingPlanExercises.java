package com.jelenazaja.fitbuddy.workout_service.plan.api;

import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlanExercise;

import java.util.Collection;

public record TrainingPlanExercises(Collection<TrainingPlanExercise> exercises) {}

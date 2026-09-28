package com.jelenazaja.fitbuddy.workout_service.exercise.api;

import com.jelenazaja.fitbuddy.workout_service.exercise.Exercise;

import java.util.Collection;

public record Exercises(Collection<Exercise> exercises) {
}
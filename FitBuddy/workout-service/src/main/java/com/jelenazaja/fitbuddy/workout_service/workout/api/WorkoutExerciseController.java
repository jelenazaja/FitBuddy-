package com.jelenazaja.fitbuddy.workout_service.workout.api;

import com.jelenazaja.fitbuddy.workout_service.workout.ExerciseSet;
import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class WorkoutExerciseController {

    private final WorkoutService workoutService;

    public WorkoutExerciseController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    // POST /workout-exercises/{id}/sets
    @PostMapping("/workout-exercises/{id}/sets")
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseSet addExerciseSet(
            @PathVariable Long id,
            @Valid @RequestBody ExerciseSet exerciseSet
    ) {
        return workoutService.addExerciseSet(id, exerciseSet);
    }

    // GET /workout-exercises/{id}/sets
    @GetMapping("/workout-exercises/{id}/sets")
    public Collection<ExerciseSet> getExerciseSets(@PathVariable Long id) {
        return workoutService.getExerciseSets(id);
    }

    // DELETE /exercise-sets/{id}
    @DeleteMapping("/exercise-sets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExerciseSetById(@PathVariable Long id) {
        workoutService.deleteExerciseSetById(id);
    }
}
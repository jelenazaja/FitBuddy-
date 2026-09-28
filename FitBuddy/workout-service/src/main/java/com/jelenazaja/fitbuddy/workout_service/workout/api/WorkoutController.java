package com.jelenazaja.fitbuddy.workout_service.workout.api;

import com.jelenazaja.fitbuddy.workout_service.workout.Workout;
import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutExercise;
import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    //WORKOUT

    // GET /workouts
    // GET /workouts?name=Leg day
    @GetMapping
    public Object findAllOrFindByName(@RequestParam(required = false) String name) {
        if (name != null) {
            return workoutService.findByName(name);
        }

        return workoutService.findAll();
    }

    // GET /workouts/{id}
    @GetMapping("/{id}")
    public Workout findById(@PathVariable Long id) {
        return workoutService.findById(id);
    }

    // POST /workouts
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Workout create(@Valid @RequestBody Workout workout) {
        return workoutService.create(workout);
    }

    // DELETE /workouts/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        workoutService.deleteById(id);
    }

    //WORKOUT EXERCISE

    // POST /workouts/{workoutId}/workout_exercises
    @PostMapping("/{workoutId}/workout_exercises")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutExercise addExercise(
            @PathVariable Long workoutId,
            @Valid @RequestBody WorkoutExercise workoutExercise
    ) {
        return workoutService.addExercise(workoutId, workoutExercise);
    }

    // GET /workouts/{id}/workout_exercises
    @GetMapping("/{id}/workout_exercises")
    public Collection<WorkoutExercise> getWorkoutExercises(@PathVariable Long id) {
        return workoutService.getWorkoutExercises(id);
    }

    // DELETE /workouts/{workoutId}/workout_exercises/{id}
    @DeleteMapping("/{workoutId}/workout_exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkoutExercise(
            @PathVariable Long workoutId,
            @PathVariable Long id
    ) {
        workoutService.deleteWorkoutExercise(workoutId, id);
    }
}
package com.jelenazaja.fitbuddy.workout_service.exercise.api;

import com.jelenazaja.fitbuddy.workout_service.exercise.Exercise;
import com.jelenazaja.fitbuddy.workout_service.exercise.ExerciseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    //GET /exercises
    //GET /exercises?muscleGroup=legs
    @GetMapping
    public Exercises getAll(@RequestParam(required = false) String muscleGroup) {
        if (muscleGroup != null) {
            return new Exercises(exerciseService.findByMuscleGroup(muscleGroup));
        }

        return new Exercises(exerciseService.findAll());
    }

    // GET /exercises/1
    @GetMapping("/{id}")
    public Exercise getById(@PathVariable Long id) {
        return exerciseService.findById(id);
    }

    // GET /exercises/name/Squat
    @GetMapping("/name/{name}")
    public Exercise getByName(@PathVariable @NotBlank String name) {
        return exerciseService.findByName(name);
    }

    //POST /exercises
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise create(@Valid @RequestBody NewExerciseRequest newExercise) {
        var exercise = new Exercise(
                null,
                newExercise.name(),
                newExercise.muscleGroup(),
                newExercise.equipment(),
                newExercise.description()
        );

        return exerciseService.create(exercise);
    }

    @DeleteMapping("/name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(@PathVariable @NotBlank String name) {
        exerciseService.deleteByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        exerciseService.deleteById(id);
    }
}
package com.jelenazaja.fitbuddy.workout_service.exercise;

import com.jelenazaja.fitbuddy.workout_service.exercise.exception.ExerciseAlreadyExistsException;
import com.jelenazaja.fitbuddy.workout_service.exercise.exception.ExerciseNotFoundException;
import com.jelenazaja.fitbuddy.workout_service.exercise.exception.MissingIdExerciseException;
import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Collection<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Exercise findByName(String name) {
        return exerciseRepository.findByName(name)
                .orElseThrow(() -> new ExerciseNotFoundException(name));
    }

    public Collection<Exercise> findByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    public Exercise create(Exercise exercise) {

        exerciseRepository.findByName(exercise.name())
                .ifPresent(existingExercise -> {
                    throw new ExerciseAlreadyExistsException(exercise.name());
                });

        return exerciseRepository.save(exercise);
    }

    public void deleteByName(String name) {
        var exercise = exerciseRepository.findByName(name)
                .orElseThrow(() -> new ExerciseNotFoundException(name));

        exerciseRepository.deleteByName(exercise.name());
    }

    public Exercise findById(Long id) {
        return exerciseRepository.findById(id).orElseThrow(() -> new MissingIdExerciseException(id));
    }

    public void deleteById(Long id) {
        var exercise = exerciseRepository.findById(id).orElseThrow(() -> new MissingIdExerciseException(id));
        exerciseRepository.deleteById(id);
    }
}
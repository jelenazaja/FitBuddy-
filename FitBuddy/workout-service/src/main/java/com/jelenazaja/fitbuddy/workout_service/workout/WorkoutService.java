package com.jelenazaja.fitbuddy.workout_service.workout;

import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdExerciseSetException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdWorkoutException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdWorkoutExerciseException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingNameWorkoutException;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set.ExerciseSetRepository;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout.WorkoutRepository;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise.WorkoutExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseSetRepository exerciseSetRepository;

    public WorkoutService(WorkoutRepository workoutRepository,
                          WorkoutExerciseRepository workoutExerciseRepository,
                          ExerciseSetRepository exerciseSetRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
    }

    // GET /workouts
    public Collection<Workout> findAll() {
        return workoutRepository.findAll();
    }

    // GET /workouts/{id}
    public Workout findById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new MissingIdWorkoutException(id));
    }

    // GET /workouts?name=Leg day
    public Workout findByName(String name) {
        return workoutRepository.findByName(name)
                .orElseThrow(() -> new MissingNameWorkoutException(name));
    }

    // POST /workouts
    public Workout create(Workout workout) {
        return workoutRepository.save(workout);
    }

    // DELETE /workouts/{id}
    public void deleteById(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new MissingIdWorkoutException(id);
        }
        workoutRepository.deleteById(id);
    }

    //WORKOUT EXERCISE

    // POST /workouts/{id}/workout_exercises
    public WorkoutExercise addExercise(Long workoutId, WorkoutExercise workoutExercise) {
        return workoutExerciseRepository.save(workoutId, workoutExercise);
    }

    // GET /workouts/{id}/workout_exercises
    public Collection<WorkoutExercise> getWorkoutExercises(Long workoutId) {
        workoutRepository.findById(workoutId)
                .orElseThrow(() -> new MissingIdWorkoutException(workoutId));
        return workoutExerciseRepository.findByWorkoutId(workoutId);
    }

    // DELETE /workouts/{workoutId}/workout_exercises/{id}
    public void deleteWorkoutExercise(Long workoutId, Long id) {
        workoutRepository.findById(workoutId)
                .orElseThrow(() -> new MissingIdWorkoutException(workoutId));

        var workoutExercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new MissingIdWorkoutExerciseException(id));

        if (!workoutExercise.workoutId().equals(workoutId)) {
            throw new MissingIdWorkoutExerciseException(id);
        }

        workoutExerciseRepository.deleteById(id);
    }

    //EXERCISE SET

    // POST /workout-exercises/{id}/sets
    public ExerciseSet addExerciseSet(Long workoutExerciseId, ExerciseSet exerciseSet) {
        if (!workoutExerciseRepository.existsById(workoutExerciseId)) {
            throw new MissingIdWorkoutExerciseException(workoutExerciseId);
        }

        return exerciseSetRepository.save(workoutExerciseId, exerciseSet);
    }

    // GET /workout-exercises/{id}/sets
    public Collection<ExerciseSet> getExerciseSets(Long workoutExerciseId) {
        if (!workoutExerciseRepository.existsById(workoutExerciseId)) {
            throw new MissingIdWorkoutExerciseException(workoutExerciseId);
        }

        return exerciseSetRepository.findByWorkoutExerciseId(workoutExerciseId);
    }

    // DELETE /exercise-sets/{id}
    public void deleteExerciseSetById(Long id) {
        if (!exerciseSetRepository.existsById(id)) {
            throw new MissingIdExerciseSetException(id);
        }

        exerciseSetRepository.deleteById(id);
    }

}


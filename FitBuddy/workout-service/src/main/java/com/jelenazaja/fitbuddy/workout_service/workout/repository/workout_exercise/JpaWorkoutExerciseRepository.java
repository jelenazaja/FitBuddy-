package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise;

import com.jelenazaja.fitbuddy.workout_service.exercise.exception.MissingIdExerciseException;
import com.jelenazaja.fitbuddy.workout_service.exercise.repository.ExerciseEntityRepository;
import com.jelenazaja.fitbuddy.workout_service.workout.WorkoutExercise;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdWorkoutException;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout.WorkoutEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaWorkoutExerciseRepository implements WorkoutExerciseRepository {

    private final WorkoutExerciseEntityRepository workoutExerciseEntityRepository;
    private final WorkoutEntityRepository workoutEntityRepository;
    private final ExerciseEntityRepository exerciseEntityRepository;

    public JpaWorkoutExerciseRepository(WorkoutExerciseEntityRepository workoutExerciseEntityRepository
    , WorkoutEntityRepository workoutEntityRepository, ExerciseEntityRepository exerciseEntityRepository) {
        this.workoutExerciseEntityRepository = workoutExerciseEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.exerciseEntityRepository = exerciseEntityRepository;
    }

    @Transactional
    public WorkoutExercise save(Long workoutId, WorkoutExercise workoutExercise) {
        var workoutEntity = workoutEntityRepository.findById(workoutId)
                .orElseThrow(() -> new MissingIdWorkoutException(workoutId));

        var exerciseEntity = exerciseEntityRepository.findById(workoutExercise.exerciseId())
                .orElseThrow(() -> new MissingIdExerciseException(workoutExercise.exerciseId()));

        var workoutExerciseEntity = WorkoutExerciseEntity.from(workoutExercise);

        workoutExerciseEntity.setWorkout(workoutEntity);
        workoutExerciseEntity.setExercise(exerciseEntity);

        var savedEntity = workoutExerciseEntityRepository.save(workoutExerciseEntity);

        return savedEntity.toWorkoutExercise();
    }

    @Override
    public Optional<WorkoutExercise> findById(Long id) {
        return workoutExerciseEntityRepository.findById(id)
                .map(WorkoutExerciseEntity::toWorkoutExercise);
    }

    public List<WorkoutExercise> findByWorkoutId(Long workoutId) {
        return workoutExerciseEntityRepository
                .findByWorkoutId(workoutId)
                .stream()
                .map(WorkoutExerciseEntity::toWorkoutExercise)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return workoutExerciseEntityRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        workoutExerciseEntityRepository.deleteById(id);
    }
}

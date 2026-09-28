package com.jelenazaja.fitbuddy.workout_service.workout.repository.exercise_set;

import com.jelenazaja.fitbuddy.workout_service.workout.ExerciseSet;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdWorkoutExerciseException;
import com.jelenazaja.fitbuddy.workout_service.workout.repository.workout_exercise.WorkoutExerciseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaExerciseSetRepository implements ExerciseSetRepository {

    private final ExerciseSetEntityRepository exerciseSetEntityRepository;
    private final WorkoutExerciseEntityRepository workoutExerciseEntityRepository;

    public JpaExerciseSetRepository(ExerciseSetEntityRepository exerciseSetEntityRepository,
                                    WorkoutExerciseEntityRepository workoutExerciseEntityRepository) {
        this.exerciseSetEntityRepository = exerciseSetEntityRepository;
        this.workoutExerciseEntityRepository = workoutExerciseEntityRepository;
    }


    @Override
    public Collection<ExerciseSet> findByWorkoutExerciseId(Long workoutExerciseId) {
        return exerciseSetEntityRepository
                .findByWorkoutExerciseIdOrderBySetNumberAsc(workoutExerciseId)
                .stream()
                .map(ExerciseSetEntity::toExerciseSet)
                .toList();
    }

    @Override
    public Optional<ExerciseSet> findById(Long id) {
        return exerciseSetEntityRepository
                .findById(id)
                .map(ExerciseSetEntity::toExerciseSet);
    }

    @Override
    public ExerciseSet save(Long workoutExerciseId, ExerciseSet exerciseSet) {
        var workoutExerciseEntity = workoutExerciseEntityRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new MissingIdWorkoutExerciseException(workoutExerciseId));

        var exerciseSetEntity = ExerciseSetEntity.from(exerciseSet);

        exerciseSetEntity.setWorkoutExercise(workoutExerciseEntity);

        var savedEntity = exerciseSetEntityRepository.save(exerciseSetEntity);

        return savedEntity.toExerciseSet();
    }

    @Override
    public void deleteByWorkoutExerciseId(Long workoutExerciseId) {
        exerciseSetEntityRepository.deleteByWorkoutExerciseId(workoutExerciseId);
    }

    @Override
    public void deleteById(Long id) {
        exerciseSetEntityRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return exerciseSetEntityRepository.existsById(id);
    }
}

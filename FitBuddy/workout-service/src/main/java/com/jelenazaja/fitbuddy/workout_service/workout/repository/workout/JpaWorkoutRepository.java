package com.jelenazaja.fitbuddy.workout_service.workout.repository.workout;

import com.jelenazaja.fitbuddy.workout_service.workout.Workout;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaWorkoutRepository implements WorkoutRepository {

    private final WorkoutEntityRepository workoutEntityRepository;

    public JpaWorkoutRepository(WorkoutEntityRepository workoutEntityRepository) {
        this.workoutEntityRepository = workoutEntityRepository;
    }

    @Override
    public Collection<Workout> findAll() {
        return workoutEntityRepository.findAll()
                .stream()
                .map(WorkoutEntity::toWorkout)
                .toList();
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return workoutEntityRepository.findById(id)
                .map(WorkoutEntity::toWorkout);
    }

    @Override
    public Optional<Workout> findByName(String name) {
        return workoutEntityRepository.findByName(name)
                .map(WorkoutEntity::toWorkout);
    }

    @Override
    public Workout save(Workout workout) {
        return workoutEntityRepository.save(WorkoutEntity.from(workout))
                .toWorkout();
    }

    @Override
    public void deleteById(Long id) {
        workoutEntityRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return workoutEntityRepository.existsById(id);
    }
}

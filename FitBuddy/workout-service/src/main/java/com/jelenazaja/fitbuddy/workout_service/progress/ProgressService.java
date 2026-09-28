package com.jelenazaja.fitbuddy.workout_service.progress;

import com.jelenazaja.fitbuddy.workout_service.progress.records.*;
import com.jelenazaja.fitbuddy.workout_service.progress.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public ExerciseProgress findProgressByExerciseId(Long exerciseId) {
        return new ExerciseProgress(
                exerciseId,
                progressRepository.findProgressByExerciseId(exerciseId)
        );
    }

    public ExerciseMaxWeight findMaxWeightByExerciseId(Long exerciseId) {
        return new ExerciseMaxWeight(
                exerciseId,
                progressRepository.findMaxWeightByExerciseId(exerciseId)
        );
    }

    public ExerciseVolume findVolumeByExerciseId(Long exerciseId) {
        return new ExerciseVolume(
                exerciseId,
                progressRepository.findVolumeByExerciseId(exerciseId)
        );
    }

    public ProgressSummary getSummary() {
        return new ProgressSummary(
                progressRepository.countWorkouts(),
                progressRepository.countSets(),
                progressRepository.sumTotalVolume(),
                progressRepository.findMaxWeight()
        );
    }

    public ProgressSummaryResponse getSummaryAI(Long exerciseId) {
        return getExerciseSummary(exerciseId);
    }

    public ProgressSummaryResponse getExerciseSummary(Long exerciseId) {
        List<ExerciseProgressEntry> progress =
                progressRepository.findProgressByExerciseId(exerciseId);

        List<ExerciseVolumeEntry> volumes =
                progressRepository.findVolumeByExerciseId(exerciseId);

        long totalSets = progress.size();

        long totalWorkouts = volumes.size();

        double maxWorkoutVolume = volumes.stream()
                .mapToDouble(ExerciseVolumeEntry::volume)
                .max()
                .orElse(0.0);

        double maxWeight =
                progressRepository.findMaxWeightByExerciseId(exerciseId);

        return new ProgressSummaryResponse(
                exerciseId,
                totalWorkouts,
                totalSets,
                maxWorkoutVolume,
                maxWeight
        );
    }
}

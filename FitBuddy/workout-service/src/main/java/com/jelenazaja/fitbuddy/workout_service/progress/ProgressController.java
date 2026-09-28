package com.jelenazaja.fitbuddy.workout_service.progress;

import com.jelenazaja.fitbuddy.workout_service.progress.records.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/exercises/{exerciseId}")
    public ExerciseProgress getExerciseProgress(@PathVariable Long exerciseId) {
        return progressService.findProgressByExerciseId(exerciseId);
    }

    @GetMapping("/exercises/{exerciseId}/max-weight")
    public ExerciseMaxWeight getMaxWeight(@PathVariable Long exerciseId) {
        return progressService.findMaxWeightByExerciseId(exerciseId);
    }

    @GetMapping("/exercises/{exerciseId}/volume")
    public ExerciseVolume getVolume(@PathVariable Long exerciseId) {
        return progressService.findVolumeByExerciseId(exerciseId);
    }

    @GetMapping("/summary")
    public ProgressSummary getSummary() {
        return progressService.getSummary();
    }


    @GetMapping("/exercises/{exerciseId}/summary")
    public ProgressSummaryResponse getExerciseSummary(
            @PathVariable Long exerciseId
    ) {
        return progressService.getExerciseSummary(exerciseId);
    }
}

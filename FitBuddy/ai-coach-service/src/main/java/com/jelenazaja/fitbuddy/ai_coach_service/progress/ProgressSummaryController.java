package com.jelenazaja.fitbuddy.ai_coach_service.progress;

import com.jelenazaja.fitbuddy.ai_coach_service.metrics.AiMetrics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/progress")
public class ProgressSummaryController {

    private final ProgressSummaryService progressSummaryService;
    private final AiMetrics aiMetrics;

    public ProgressSummaryController(
            ProgressSummaryService progressSummaryService,
            AiMetrics aiMetrics
    ) {
        this.progressSummaryService = progressSummaryService;
        this.aiMetrics = aiMetrics;
    }

    @GetMapping("/summary")
    public ResponseEntity<AiProgressSummary> generateSummary() {

        aiMetrics.incrementRequest("progress_summary");
        AiProgressSummary summary =
                progressSummaryService.generateSummary();

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/summary/{exerciseId}")
    public ResponseEntity<AiProgressSummary> generateExerciseSummary(
            @PathVariable Long exerciseId
    ) {
        aiMetrics.incrementRequest("progress_summary_exercise");
        AiProgressSummary summary =
                progressSummaryService.generateSummary(exerciseId);

        return ResponseEntity.ok(summary);
    }
}

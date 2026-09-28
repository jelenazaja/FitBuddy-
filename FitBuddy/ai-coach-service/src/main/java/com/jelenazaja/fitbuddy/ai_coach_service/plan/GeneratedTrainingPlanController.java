package com.jelenazaja.fitbuddy.ai_coach_service.plan;

import com.jelenazaja.fitbuddy.ai_coach_service.metrics.AiMetrics;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/training-plans")
public class GeneratedTrainingPlanController {

    private final GeneratedTrainingPlanService generatedTrainingPlanService;
    private final AiMetrics aiMetrics;

    public GeneratedTrainingPlanController(
            GeneratedTrainingPlanService generatedTrainingPlanService,
            AiMetrics aiMetrics
    ) {
        this.generatedTrainingPlanService = generatedTrainingPlanService;
        this.aiMetrics = aiMetrics;
    }

    @PostMapping("/generate")
    public ResponseEntity<GeneratedTrainingPlan> generatePlan(
            @Valid @RequestBody GenerateTrainingPlanRequest request
    ) {
        aiMetrics.incrementRequest("plan_generation");
        GeneratedTrainingPlan generatedPlan =
                generatedTrainingPlanService.generatePlan(request);

        return ResponseEntity.ok(generatedPlan);
    }
}

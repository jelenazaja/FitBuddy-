package com.jelenazaja.fitbuddy.ai_coach_service.exercise;

import com.jelenazaja.fitbuddy.ai_coach_service.metrics.AiMetrics;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/exercises")
public class ExerciseExplanationController {

    private final ExerciseExplanationService service;
    private final AiMetrics aiMetrics;

    public ExerciseExplanationController(
            ExerciseExplanationService service,
            AiMetrics aiMetrics
    ) {
        this.service = service;
        this.aiMetrics = aiMetrics;
    }

    @PostMapping("/explain")
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseExplanationResponse explain(
            @Valid @RequestBody ExplainExerciseRequest request
    ) {
        aiMetrics.incrementRequest("exercise_explanation");
        return service.explain(request.exerciseId());
    }
}
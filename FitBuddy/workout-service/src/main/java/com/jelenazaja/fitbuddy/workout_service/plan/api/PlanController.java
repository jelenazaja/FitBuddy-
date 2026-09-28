package com.jelenazaja.fitbuddy.workout_service.plan.api;

import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlan;
import com.jelenazaja.fitbuddy.workout_service.plan.TrainingPlanExercise;
import com.jelenazaja.fitbuddy.workout_service.plan.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    //TRAINING PLAN

    // GET /training-plans
    @GetMapping("/training-plans")
    public TrainingPlans findAll() {
        return new TrainingPlans(planService.findAll());
    }

    // GET /training-plans/{id}
    @GetMapping("/training-plans/{id}")
    public TrainingPlan findById(@PathVariable Long id) {
        return planService.findById(id);
    }

    // POST /training-plans
    @PostMapping("/training-plans")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingPlan create(@Valid @RequestBody TrainingPlan trainingPlan) {
        return planService.save(trainingPlan);
    }

    // DELETE /training-plans/{id}
    @DeleteMapping("/training-plans/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        planService.deleteById(id);
    }

    //TRAINING PLAN EXERCISES

    // GET /training-plans/{planId}/exercises
    @GetMapping("/training-plans/{planId}/exercises")
    public TrainingPlanExercises findExercisesByPlanId(@PathVariable Long planId) {
        return new TrainingPlanExercises(planService.findExercisesByPlanId(planId));
    }

    // POST /training-plans/{planId}/exercises
    @PostMapping("/training-plans/{planId}/exercises")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingPlanExercise addExerciseToPlan(
            @PathVariable Long planId,
            @Valid @RequestBody TrainingPlanExercise trainingPlanExercise
    ) {
        return planService.addExerciseToPlan(planId, trainingPlanExercise);
    }

    // DELETE /training-plan-exercises/{id}
    @DeleteMapping("/training-plan-exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainingPlanExercise(@PathVariable Long id) {
        planService.deleteTrainingPlanExercise(id);
    }
}

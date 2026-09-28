package com.jelenazaja.fitbuddy.workout_service.api;

import com.jelenazaja.fitbuddy.workout_service.exercise.exception.ExerciseAlreadyExistsException;
import com.jelenazaja.fitbuddy.workout_service.exercise.exception.ExerciseNotFoundException;
import com.jelenazaja.fitbuddy.workout_service.exercise.exception.MissingIdExerciseException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdExerciseSetException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdWorkoutException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingIdWorkoutExerciseException;
import com.jelenazaja.fitbuddy.workout_service.workout.exception.MissingNameWorkoutException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExerciseNotFound(ExerciseNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ExerciseAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleExerciseAlreadyExists(ExerciseAlreadyExistsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MissingIdExerciseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMissingIdExercise(MissingIdExerciseException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MissingIdWorkoutException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMissingIdWorkout(MissingIdWorkoutException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MissingNameWorkoutException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMissingNameWorkout(MissingNameWorkoutException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MissingIdWorkoutExerciseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMissingIdWorkoutExercise(MissingIdWorkoutExerciseException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MissingIdExerciseSetException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMissingIdExerciseSet(MissingIdExerciseSetException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        var message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request.");

        return new ErrorResponse(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException exception) {
        var message = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .findFirst()
                .orElse("Invalid request.");

        return new ErrorResponse(message);
    }

    public record ErrorResponse(String message) {}
}
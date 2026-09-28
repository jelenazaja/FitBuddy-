package com.jelenazaja.fitbuddy.ai_coach_service.api;
import com.jelenazaja.fitbuddy.ai_coach_service.exception.MissingIdExerciseException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlers{

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Error> handleRateLimitExceeded(RequestNotPermitted exception) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new Error("Too many requests to the AI model. Please try again later."));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolation(ConstraintViolationException exception) {
        var message = exception.getConstraintViolations()
                .stream()
                .map(v -> "%s: %s".formatted(
                        v.getPropertyPath().toString().replaceFirst(".*\\.", ""),
                        v.getMessage()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("Validation error.");

        return ResponseEntity.badRequest().body(new Error(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationException(MethodArgumentNotValidException exception) {
        var message = exception.getFieldErrors()
                .stream()
                .map(fieldError -> "%s: %s".formatted(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("Validation error.");

        return ResponseEntity.badRequest()
                .body(new Error(message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleInvalidJson(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
                .body(new Error("Invalid request body."));
    }

    @ExceptionHandler(MissingIdExerciseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMissingExercise(
            MissingIdExerciseException exception
    ) {
        return new ErrorResponse(exception.getMessage());
    }

    public record ErrorResponse(String message) {}
}
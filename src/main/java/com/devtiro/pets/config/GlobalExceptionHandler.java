package com.devtiro.pets.config;

import com.devtiro.pets.domain.dto.security.ErrorResponse;
import com.devtiro.pets.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest servletRequest) {
        log.warn("User already exists: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("USER_ALREADY_EXISTS")
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .error("CONFLICT")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest servletRequest) {
        log.warn("User not found: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("USER_NOT_FOUND")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest servletRequest) {
        log.warn("Username not found: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("USER_NOT_FOUND")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("BAD_REQUEST")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest servletRequest) {
        log.warn("Unauthorized access: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("UNAUTHORIZED_ACCESS")
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UserAccountDisabledException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountDisabledException(UserAccountDisabledException ex, HttpServletRequest servletRequest) {
        log.warn("User account disabled: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("USER_ACCOUNT_DISABLED")
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .error("FORBIDDEN")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(UserAccountLockedException.class)
    public ResponseEntity<ErrorResponse> handleUserAccountLockedException(UserAccountLockedException ex, HttpServletRequest servletRequest) {
        log.warn("User account locked: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("USER_ACCOUNT_LOCKED")
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .error("FORBIDDEN")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest servletRequest) {
        log.warn("Invalid credentials: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("INVALID_CREDENTIALS")
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshToken(InvalidRefreshTokenException ex, HttpServletRequest servletRequest) {
        log.warn("Invalid refresh token: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("INVALID_REFRESH_TOKEN")
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponse> handleAccountLocked(AccountLockedException ex, HttpServletRequest servletRequest) {
        log.warn("Account locked: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("ACCOUNT_LOCKED")
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .error("FORBIDDEN")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest servletRequest) {
        log.warn("Bad credentials: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("INVALID_CREDENTIALS")
                .message("Invalid email or password")
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest servletRequest) {
        log.warn("Access denied: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("ACCESS_DENIED")
                .message("You don't have permission to access this resource")
                .status(HttpStatus.FORBIDDEN.value())
                .error("FORBIDDEN")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePetNotFoundException(PetNotFoundException ex, HttpServletRequest servletRequest) {
        log.warn("Pet not found: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("PET_NOT_FOUND")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMedicalRecordNotFoundException(MedicalRecordNotFoundException ex, HttpServletRequest servletRequest) {
        log.warn("Medical record not found: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("MEDICAL_RECORD_NOT_FOUND")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidApplicationStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidApplicationStatusException(InvalidApplicationStatusException ex, HttpServletRequest servletRequest) {
        log.warn("Invalid application status: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("INVALID_APPLICATION_STATUS")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("INVALID")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateApplicationException(DuplicateApplicationException ex, HttpServletRequest servletRequest) {
        log.error("Duplicate application: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("DUPLICATE_APPLICATION")
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .error("Duplicate Application")
                .path(servletRequest.getRequestURI())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Validation errors: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest servletRequest) {
        log.warn("Illegal argument: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("INVALID_REQUEST")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("BAD_REQUEST")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex, HttpServletRequest servletRequest) {
        log.warn("Illegal state: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .code("INVALID_STATE")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("BAD_REQUEST")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest servletRequest) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse error = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred. Please try again later.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("INTERNAL_SERVER_ERROR")
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
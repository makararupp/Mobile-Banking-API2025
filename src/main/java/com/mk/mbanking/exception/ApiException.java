package com.mk.mbanking.exception;

import com.mk.mbanking.base.ErrorApi;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {

    //Exception for user register new account
    @ExceptionHandler(SQLException.class)
    public ErrorApi<?> handleSQLException(SQLException e){
        String sqlState = e.getSQLState();
        String errorMessage = e.getMessage();
        Map<String, String> errors = new HashMap<>();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String customMessage = "Database error occurred";

        // Handle specific SQL error codes
        switch (sqlState){
            case "28P01": // Invalid password
                errors.put("authentication","Invalid database password");
                status = HttpStatus.UNAUTHORIZED;
                customMessage = "Database authentication failed";
                break;

            case  "28000": // Invalid authorization specification
                errors.put("authorization", "Invalid role or user does not exist");
                status = HttpStatus.UNAUTHORIZED;
                customMessage = "Database role authentication failed";
                break;

            case "3D000": // Database does not exist
                errors.put("database", "Requested database does not exist");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                customMessage = "Database configuration error";
                break;

            case "23505": // Unique constraint violation
                errors.put("constraint", extractConstraintName(errorMessage));
                status = HttpStatus.BAD_REQUEST;
                customMessage = "Duplicate resource violation";
                break;

            case "23503": // Foreign key violation
                errors.put("foreign_key", "Referenced resource does not exist");
                status = HttpStatus.BAD_REQUEST;
                customMessage = "Reference constraint violation";
                break;

            case "23502": // Not null violation
                errors.put("null_constraint", "Required field cannot be null");
                status = HttpStatus.BAD_REQUEST;
                customMessage = "Null constraint violation";
                break;

            case "42703": // Undefined column
                errors.put("schema", "Requested column does not exist");
                status = HttpStatus.BAD_REQUEST;
                customMessage = "Schema validation error";
                break;

            case "42P01": // Undefined table
                errors.put("schema", "Requested table does not exist");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                customMessage = "Database schema error";
                break;

            case "40P01": // Deadlock detected
                errors.put("concurrency", "Database deadlock detected");
                status = HttpStatus.CONFLICT;
                customMessage = "Concurrency conflict";
                break;

            default:
                errors.put("database", "Unexpected database error: " + sqlState);
                errors.put("detail", errorMessage.length() > 200 ? errorMessage.substring(0, 200) + "..." : errorMessage);
                break;
        }
        return ErrorApi.builder()
                .status(false)
                .code(status.value())
                .message(customMessage)
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }

    // Helper method to extract constraint name from error message
    private String extractConstraintName(String errorMessage) {
        if (errorMessage.contains("constraint")) {
            String[] parts = errorMessage.split("constraint");
            if (parts.length > 1) {
                String constraintPart = parts[1].split("'")[1];
                return constraintPart;
            }
        }
        return "unique_constraint_violation";
    }


    //Exception catch to field errors in field detail.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorApi<?> handleValidationException(MethodArgumentNotValidException e){
        List<Map<String, String>> errors = new ArrayList<>();
        //using foreach
        for (FieldError fieldError : e.getFieldErrors()){
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("detail", fieldError.getDefaultMessage());
            errors.add(error);
        }
        return ErrorApi.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
    //exception update information
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorApi<?> handleServiceException(ResponseStatusException e){
        return ErrorApi.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .errors(e.getReason())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorApi<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        // Create error details map
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("resource", ex.getResourceName());
        errorDetails.put("field", ex.getFieldName());
        errorDetails.put("value", ex.getFieldValue());
        errorDetails.put("path", request.getRequestURI());

        // Use the builder pattern to create ErrorApi
        ErrorApi<Object> errorResponse = ErrorApi.builder()
                .status(false)
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(errorDetails)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
package com.sanketika.course_backend.excepections;

import com.sanketika.course_backend.utils.ApiEnvelope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom "not found" errors (like missing course or unit)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiEnvelope<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiEnvelope<Void> response = new ApiEnvelope<>();
        response.setId("api.error");
        response.setVer("v1");
        response.setTs(Instant.now().toString());
        response.getParams().setMsgid(UUID.randomUUID().toString());
        response.getParams().setStatus("failed");
        response.getParams().setErr("RESOURCE_NOT_FOUND");
        response.getParams().setErrmsg(ex.getMessage());
        response.setResponseCode("RESOURCE_NOT_FOUND");
        response.setResult(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle unexpected runtime errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiEnvelope<Void>> handleRuntimeException(RuntimeException ex) {
        ApiEnvelope<Void> response = new ApiEnvelope<>();
        response.setId("api.error");
        response.setVer("v1");
        response.setTs(Instant.now().toString());
        response.getParams().setMsgid(UUID.randomUUID().toString());
        response.getParams().setStatus("failed");
        response.getParams().setErr("INTERNAL_SERVER_ERROR");
        response.getParams().setErrmsg(ex.getMessage());
        response.setResponseCode("INTERNAL_SERVER_ERROR");
        response.setResult(null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Handle all other exceptions (generic fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiEnvelope<Void>> handleGenericException(Exception ex) {
        ApiEnvelope<Void> response = new ApiEnvelope<>();
        response.setId("api.error");
        response.setVer("v1");
        response.setTs(Instant.now().toString());
        response.getParams().setMsgid(UUID.randomUUID().toString());
        response.getParams().setStatus("failed");
        response.getParams().setErr("BAD_REQUEST");
        response.getParams().setErrmsg(ex.getMessage());
        response.setResponseCode("BAD_REQUEST");
        response.setResult(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

package Proyect.IoTParkers.shared.interfaces.rest;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex) {
        var errors = new ArrayList<Map<String,String>>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> {
            errors.add(Map.of("field", fe.getField(), "message", fe.getDefaultMessage()));
        });
        var body = new LinkedHashMap<String,Object>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("messages", errors);
        return ResponseEntity.badRequest().body(body);
    }
}
package com.cust.poc.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        Map<String, Object> body=new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all fields errors
        String errors = ex.getStackTrace().toString();

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}

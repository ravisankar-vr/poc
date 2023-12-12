package com.cust.poc.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseObj){
        Map<String, Object> result=new HashMap<String, Object>();
        result.put("message",message);
        result.put("status", httpStatus);
        result.put("data", responseObj);

        return new ResponseEntity<Object>(result, httpStatus);
    }
}

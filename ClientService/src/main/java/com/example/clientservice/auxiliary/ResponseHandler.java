package com.example.clientservice.auxiliary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<BigDecimal> generateResponse(String message, HttpStatus httpStatus, BigDecimal body) {
        Map<String, Object> source = new HashMap<>();
        source.put("message", message);
        source.put("status", httpStatus.value());
        source.put("data", body);
        return new ResponseEntity<Object>(source, httpStatus);
    }
}

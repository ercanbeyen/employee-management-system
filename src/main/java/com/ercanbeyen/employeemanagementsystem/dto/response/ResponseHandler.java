package com.ercanbeyen.employeemanagementsystem.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean success, String message, Object data) {
        Map<String, Object> map = new HashMap<>();

        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("success", success);
            map.put("message", message);
            map.put("data", data);
        } catch (Exception exception) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("success", false);
            map.put("message", exception.getMessage());
            map.put("data", null);
        }

        return new ResponseEntity<>(map, status);
    }
}

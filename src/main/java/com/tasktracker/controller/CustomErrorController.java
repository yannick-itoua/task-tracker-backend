package com.tasktracker.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError() {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "This is a REST API. Please use /api endpoints.");
        errorResponse.put("status", 404);
        errorResponse.put("availableEndpoints", new String[]{
            "GET /api/tasks",
            "POST /api/tasks", 
            "PUT /api/tasks/{id}",
            "DELETE /api/tasks/{id}",
            "GET /actuator/health"
        });
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @RequestMapping("/")
    public ResponseEntity<Map<String, Object>> handleRoot() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task Tracker API");
        response.put("status", "running");
        response.put("endpoints", new String[]{
            "GET /api/tasks - Get all tasks",
            "POST /api/tasks - Create a new task", 
            "PUT /api/tasks/{id} - Update a task",
            "DELETE /api/tasks/{id} - Delete a task",
            "GET /actuator/health - Health check"
        });
        
        return ResponseEntity.ok(response);
    }
}
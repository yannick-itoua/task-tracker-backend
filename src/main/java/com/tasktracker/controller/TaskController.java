package com.tasktracker.controller;

import com.tasktracker.dto.TaskRequest;
import com.tasktracker.dto.TaskResponse;
import com.tasktracker.entity.Task;
import com.tasktracker.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    // GET /api/tasks - Get all tasks
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String search) {
        
        List<Task> tasks;
        
        if (search != null && !search.trim().isEmpty()) {
            tasks = taskRepository.findByTitleContainingIgnoreCase(search.trim());
        } else if (done != null) {
            tasks = taskRepository.findByDoneOrderByCreatedAtDesc(done);
        } else {
            tasks = taskRepository.findAllByOrderByCreatedAtDesc();
        }
        
        List<TaskResponse> taskResponses = tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(taskResponses);
    }
    
    // GET /api/tasks/{id} - Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        
        if (task.isPresent()) {
            return ResponseEntity.ok(new TaskResponse(task.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDone(taskRequest.isDone());
        
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskResponse(savedTask));
    }
    
    // PUT /api/tasks/{id} - Update existing task
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id, 
            @Valid @RequestBody TaskRequest taskRequest) {
        
        Optional<Task> existingTask = taskRepository.findById(id);
        
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setDone(taskRequest.isDone());
            
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(new TaskResponse(updatedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PATCH /api/tasks/{id}/toggle - Toggle task completion status
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskResponse> toggleTaskCompletion(@PathVariable Long id) {
        Optional<Task> existingTask = taskRepository.findById(id);
        
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setDone(!task.isDone());
            
            Task updatedTask = taskRepository.save(task);
            return ResponseEntity.ok(new TaskResponse(updatedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/tasks/{id} - Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET /api/tasks/stats - Get task statistics
    @GetMapping("/stats")
    public ResponseEntity<TaskStats> getTaskStats() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByDone(true);
        long pendingTasks = taskRepository.countByDone(false);
        
        TaskStats stats = new TaskStats(totalTasks, completedTasks, pendingTasks);
        return ResponseEntity.ok(stats);
    }
    
    // Inner class for task statistics
    public static class TaskStats {
        private long total;
        private long completed;
        private long pending;
        
        public TaskStats(long total, long completed, long pending) {
            this.total = total;
            this.completed = completed;
            this.pending = pending;
        }
        
        // Getters
        public long getTotal() { return total; }
        public long getCompleted() { return completed; }
        public long getPending() { return pending; }
    }
}
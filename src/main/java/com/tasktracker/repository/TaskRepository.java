package com.tasktracker.repository;

import com.tasktracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Find all tasks ordered by creation date (newest first)
    List<Task> findAllByOrderByCreatedAtDesc();
    
    // Find tasks by completion status
    List<Task> findByDone(boolean done);
    
    // Find tasks by completion status ordered by creation date
    List<Task> findByDoneOrderByCreatedAtDesc(boolean done);
    
    // Find tasks containing specific text in title (case insensitive)
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    // Count completed tasks
    long countByDone(boolean done);
}
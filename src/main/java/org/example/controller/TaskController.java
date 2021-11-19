package org.example.controller;

import org.example.domain.Task;
import org.example.domain.TaskDto;
import org.example.exception.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private List<Task> taskList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskList, OK);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody @Valid TaskDto taskDto) {
        Task t = new Task(taskDto.getName(), taskDto.getDescription());
        taskList.add(t);

        return new ResponseEntity<>(t, OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        Task task = taskList.stream()
                .filter(t -> t.getId() == taskId)
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return new ResponseEntity<>(task, OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> changeTask(@PathVariable Long taskId, @Valid @RequestBody TaskDto taskDto) {
        Task task = taskList.stream()
                .peek(t -> {
                    if (t.getId() == taskId) {
                        t.setName(taskDto.getName());
                        t.setDescription(taskDto.getDescription());
                        t.setLastModificationDate(LocalDateTime.now());
                    }
                })
                .filter(t -> t.getId() == taskId)
                .findAny()
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return new ResponseEntity<>(task, OK);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        if(taskList.stream().anyMatch(t -> t.getId() == taskId)) {
            taskList.removeIf(t -> t.getId() == taskId);
        } else
            throw new TaskNotFoundException(taskId);
    }

    @GetMapping("/sort-by-modification-date")
    public List<Task> getAllTasksSortedByModificationDate() {
        return taskList.stream()
                .sorted(Comparator.comparing(Task::getLastModificationDate))
                .collect(Collectors.toList());
    }
}
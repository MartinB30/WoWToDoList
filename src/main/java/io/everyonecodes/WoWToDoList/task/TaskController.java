package io.everyonecodes.WoWToDoList.task;

import io.everyonecodes.WoWToDoList.character.WarcraftCharacter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get All Tasks", description = "Retrieves a list of all tasks.")
    List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Task by ID", description = "Retrieves details of a specific task based on the provided ID.")
    Optional<Task> findById(@PathVariable Long id){
        return Optional.of(service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id " + id)));
    }

    @GetMapping("/character/{id}")
    @Operation(summary = "Get Tasks by Character ID", description = "Retrieves a list of tasks associated with a specific character based on the provided ID.")
    public Optional<List<Task>> findByCharacterId(@PathVariable Long id) {
        return Optional.of(service.findByCharacterId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
    }

    @PostMapping
    @Operation(summary = "Create Task", description = "Creates a new task.")
    Task createTask(@RequestBody Task task) {
        return service.createTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Task by ID", description = "Updates the details of a specific task based on the provided ID.")
    Task updateTask(@RequestBody Task updateTask, @PathVariable Long id) {
        return service.updateTask(updateTask, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task by ID", description = "Deletes a specific task based on the provided ID.")
    void deleteTask(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Update Complete Status", description = "Updates the complete status of a specific task based on the provided ID.")
    public ResponseEntity<Task> updateCompleteStatus(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = service.updateCompleteStatus(id, task.isCompleted());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}

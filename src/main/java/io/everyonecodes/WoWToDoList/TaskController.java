package io.everyonecodes.WoWToDoList;

import io.everyonecodes.WoWToDoList.customExceptions.BadRequestException;
import org.springframework.http.HttpStatus;
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
    public List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable Long id){
        return Optional.of(service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id " + id)));
    }

    @GetMapping("/characters/{id}")
    public Optional<List<Task>> findByCharacterId(@PathVariable Long id) {
        return Optional.of(service.findByCharacterId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        if (task.getCharacter() == null || task.getDescription() == null || task.getTaskName() == null) {
            throw new BadRequestException("Task name, description or character ID cannot be null");
        }
        return service.createTask(task);
    }
}

package io.everyonecodes.WoWToDoList.task;

import io.everyonecodes.WoWToDoList.customExceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Optional<List<Task>> findByCharacterId(@PathVariable Long characterId) {
        return repository.findByCharacterId(characterId);
    }

    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public Task updateTask(@RequestBody Task updatedTask, @PathVariable Long id) {
        Optional<Task> oExistingTask = findById(id);
        if (oExistingTask.isPresent()) {
            Task task = oExistingTask.get();

            if (updatedTask.getTaskName() != null) {
                task.setTaskName(updatedTask.getTaskName());
            }

            if (updatedTask.getDescription() != null) {
                task.setDescription(updatedTask.getDescription());
            }

            if (updatedTask.getCharacter() != null) {
                task.setCharacter(updatedTask.getCharacter());
            }

            task.setCompleted(updatedTask.isCompleted());
            return repository.save(task);
        } else {
            return createTask(updatedTask);
        }
    }

    public Task createTask(@RequestBody Task task) {
        if (task.getCharacter() == null || task.getDescription() == null || task.getTaskName() == null) {
            throw new BadRequestException("Task name, description or character ID cannot be null");
        }
        return repository.save(task);
    }

}

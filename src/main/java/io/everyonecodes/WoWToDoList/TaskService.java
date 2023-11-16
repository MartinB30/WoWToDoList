package io.everyonecodes.WoWToDoList;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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

    public Task saveTask(Long id, @RequestBody Task updatedTask) {
        Optional<Task> existingTask = repository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTaskName(updatedTask.getTaskName());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            task.setCharacter(updatedTask.getCharacter());
            return repository.save(task);
        } else {
            Task newTask = new Task(updatedTask.getTaskName(), updatedTask.getDescription(), updatedTask.isCompleted(), updatedTask.getCharacter());
            return repository.save(newTask);
        }
    }

    public Task createTask(@RequestBody Task task) {
        return repository.save(task);
    }

}

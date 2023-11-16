package io.everyonecodes.WoWToDoList;

import io.everyonecodes.WoWToDoList.customExceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Character> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Character> findById(@PathVariable Long id) {
        return Optional.of(service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
    }

    @PostMapping
    public Character createCharacter(@RequestBody Character character) {
        return service.createCharacter(character);
    }

    @PutMapping("/{id}")
    public Character updateCharacter(@RequestBody Character character, @PathVariable Long id) {
        return service.updateCharacter(id, character);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

//    @GetMapping("/{id}/tasks")
//    public List<Task> getCharacterTaskListById(@PathVariable Long id) {
//        return service.getTaskForCharacter(id);
//    }

}

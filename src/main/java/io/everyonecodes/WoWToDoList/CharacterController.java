package io.everyonecodes.WoWToDoList;

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

}

package io.everyonecodes.WoWToDoList.character;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/character")
public class WarcraftCharacterController {

    private final WarcraftCharacterService service;

    public WarcraftCharacterController(WarcraftCharacterService service) {
        this.service = service;
    }

    @GetMapping
    List<WarcraftCharacter> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Optional<WarcraftCharacter> findById(@PathVariable Long id) {
        return Optional.of(service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
    }

    @PostMapping
    WarcraftCharacter createCharacter(@RequestBody WarcraftCharacter character) {
        character.setFavorite(false);
        return service.createCharacter(character);
    }

    @PutMapping("/{id}")
    WarcraftCharacter updateCharacter(@RequestBody WarcraftCharacter character, @PathVariable Long id) {
        return service.updateCharacter(id, character);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<WarcraftCharacter> updateFavoriteStatus(@PathVariable Long id, @RequestBody WarcraftCharacter character) {
        WarcraftCharacter updatedCharacter = service.updateFavoriteStatus(id, character.isFavorite());
        return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
    }

//    @GetMapping("/{id}/tasks")
//    public List<Task> getCharacterTaskListById(@PathVariable Long id) {
//        return service.getTaskForCharacter(id);
//    }

}

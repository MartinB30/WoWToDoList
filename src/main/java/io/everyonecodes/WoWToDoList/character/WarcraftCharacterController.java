package io.everyonecodes.WoWToDoList.character;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get All Characters", description = "Retrieves a list of all Warcraft characters.")
    List<WarcraftCharacter> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Character by ID", description = "Retrieves details of a specific Warcraft character based on the provided ID.")
    Optional<WarcraftCharacter> findById(@PathVariable Long id) {
        return Optional.of(service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
    }

    @PostMapping
    @Operation(summary = "Create Character", description = "Creates a new Warcraft character.")
    WarcraftCharacter createCharacter(@RequestBody WarcraftCharacter character) {
        character.setFavorite(false);
        return service.createCharacter(character);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Character by ID", description = "Updates the details of a specific Warcraft character based on the provided ID.")
    WarcraftCharacter updateCharacter(@RequestBody WarcraftCharacter character, @PathVariable Long id) {
        return service.updateCharacter(id, character);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Character by ID", description = "Deletes a specific Warcraft character based on the provided ID.")
    void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/favorite")
    @Operation(summary = "Update Favorite Status", description = "Updates the favorite status of a specific Warcraft character based on the provided ID.")
    public ResponseEntity<WarcraftCharacter> updateFavoriteStatus(@PathVariable Long id, @RequestBody WarcraftCharacter character) {
        WarcraftCharacter updatedCharacter = service.updateFavoriteStatus(id, character.isFavorite());
        return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
    }
}

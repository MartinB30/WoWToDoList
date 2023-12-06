package io.everyonecodes.WoWToDoList.character;

import io.everyonecodes.WoWToDoList.customExceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

@Service
public class WarcraftCharacterService {

    private final WarcraftCharacterRepository repository;

    public WarcraftCharacterService(WarcraftCharacterRepository repository) {
        this.repository = repository;
    }

    public List<WarcraftCharacter> findAll() {
        return repository.findAllByOrderByServerAscNameAsc();
    }

    public Optional<WarcraftCharacter> findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public WarcraftCharacter updateCharacter(@PathVariable Long id, @RequestBody WarcraftCharacter updatedCharacter) {
        Optional<WarcraftCharacter> existingCharacter = findById(id);
        if (updatedCharacter.getName() == null && updatedCharacter.getServer() == null) {
            throw new BadRequestException("Character name and server can not be null");
        }

        if (existingCharacter.isPresent()) {
            WarcraftCharacter character = existingCharacter.get();

            if (updatedCharacter.getName() != null) {
                character.setName(updatedCharacter.getName());
            }

            if (updatedCharacter.getServer() != null) {
                character.setServer(updatedCharacter.getServer());
            }

            return repository.save(character);
        } else {
            return createCharacter(updatedCharacter);
        }
    }

    public WarcraftCharacter createCharacter(@RequestBody WarcraftCharacter character) {
        if (character.getName() == null || character.getServer() == null) {
            throw new BadRequestException("Character name and server can not be null");
        }
        return repository.save(character);
    }

    public boolean isCharacterInDatabase(String name, String server) {
        Optional<WarcraftCharacter> oExistingCharacter = repository.findByNameAndServer(name, server);
        return oExistingCharacter.isPresent();
    }
}

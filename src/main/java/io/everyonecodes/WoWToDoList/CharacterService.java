package io.everyonecodes.WoWToDoList;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public List<Character> findAll () {
        return repository.findAll();
    }

    public Optional<Character> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public Character saveCharacter(Long id, @RequestBody Character updatedCharacter) {
        Optional<Character> existingCharacter = repository.findById(id);
        if (existingCharacter.isPresent()) {
            Character character = existingCharacter.get();
            character.setName(updatedCharacter.getName());
            character.setServer(updatedCharacter.getServer());
            return repository.save(character);
        } else {
            Character newCharacter = new Character(updatedCharacter.getName(), updatedCharacter.getServer());
            return repository.save(newCharacter);
        }
    }

    public Character saveCharacter(@RequestBody Character updatedCharacter) {
        return repository.save(updatedCharacter);
    }

}

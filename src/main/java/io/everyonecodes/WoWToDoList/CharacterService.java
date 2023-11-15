package io.everyonecodes.WoWToDoList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

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

    public Optional<Character> findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public Character updateCharacter(@PathVariable Long id, @RequestBody Character updatedCharacter) {
        Optional<Character> existingCharacter = repository.findById(id);
        if (updatedCharacter.getName() == null && updatedCharacter.getServer() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not available inputs name:" + updatedCharacter.getName() + " server:" + updatedCharacter.getServer());
        }

        if (existingCharacter.isPresent()) {
            Character character = existingCharacter.get();

            if(updatedCharacter.getName() != null){
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

    public Character createCharacter(@RequestBody Character updatedCharacter) {
        return repository.save(updatedCharacter);
    }

}

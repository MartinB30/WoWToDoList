package io.everyonecodes.WoWToDoList.character;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<WarcraftCharacter, Long> {

    Optional<WarcraftCharacter> findByNameAndServer(String name, String server);
}

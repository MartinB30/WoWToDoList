package io.everyonecodes.WoWToDoList;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findByNameAndServer(String name, String server);
}

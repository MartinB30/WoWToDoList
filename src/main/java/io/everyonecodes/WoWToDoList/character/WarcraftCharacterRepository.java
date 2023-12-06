package io.everyonecodes.WoWToDoList.character;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarcraftCharacterRepository extends JpaRepository<WarcraftCharacter, Long> {

    Optional<WarcraftCharacter> findByNameAndServer(String name, String server);

    List<WarcraftCharacter> findAllByOrderByServerAscNameAsc();
}

package io.everyonecodes.WoWToDoList.task;

import io.everyonecodes.WoWToDoList.character.WarcraftCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findByWarcraftCharacterIdOrderByIsCompletedAsc(Long id);

}

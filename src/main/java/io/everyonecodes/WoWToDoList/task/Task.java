package io.everyonecodes.WoWToDoList.task;

import io.everyonecodes.WoWToDoList.character.WarcraftCharacter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private String description;
    private boolean isCompleted;

    @ManyToOne
    private WarcraftCharacter warcraftCharacter;

    public Task(String taskName, String description, boolean isCompleted, WarcraftCharacter warcraftCharacter) {
        this.taskName = taskName;
        this.description = description;
        this.isCompleted = isCompleted;
        this.warcraftCharacter = warcraftCharacter;
    }
}

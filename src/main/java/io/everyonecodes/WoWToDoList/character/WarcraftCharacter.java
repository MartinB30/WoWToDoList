package io.everyonecodes.WoWToDoList.character;

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
public class WarcraftCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String server;
    private boolean favorite;

    public WarcraftCharacter(String name, String server, boolean favorite) {
        this.name = name;
        this.server = server;
        this.favorite = favorite;
    }
}

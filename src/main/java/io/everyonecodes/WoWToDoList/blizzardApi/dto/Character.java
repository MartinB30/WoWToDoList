package io.everyonecodes.WoWToDoList.blizzardApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {

    private String name;
    private Realm realm;
    private int level;

    public Character(String name, Realm realm, int level) {
        this.name = name;
        this.realm = realm;
        this.level = level;
    }
}

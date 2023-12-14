package io.everyonecodes.WoWToDoList.blizzardApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Realm {

    private String name;
}

package io.everyonecodes.WoWToDoList.blizzardApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    private List<Character> Characters;

}

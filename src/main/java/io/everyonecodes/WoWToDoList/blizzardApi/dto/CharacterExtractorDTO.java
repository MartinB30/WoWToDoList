package io.everyonecodes.WoWToDoList.blizzardApi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//DTO experiment
public class CharacterExtractorDTO {
    public List<String> extractCharacters(Profile profile) {

        List<String> characterNamesAndServers = new ArrayList<>();
        System.out.println(profile);
        for (Account account : profile.getWow_accounts()) {
            for (Character character : account.getCharacters()) {
                characterNamesAndServers.add(character.getName() + " - " + character.getRealm().getName() + " - " + character.getLevel());
            }
        }

        return characterNamesAndServers;
    }

    public List<String> apiMapper(String jsonString) {

        List<String> buffer = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Profile profile = objectMapper.readValue(jsonString, Profile.class);
            buffer = new ArrayList<>(extractCharacters(profile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}


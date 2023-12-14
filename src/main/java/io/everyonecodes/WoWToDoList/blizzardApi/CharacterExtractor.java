package io.everyonecodes.WoWToDoList.blizzardApi;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CharacterExtractor {

        public Map<String, String> extractCharactersNameAndServer(String json) {

        Map<String, String> characterMap = new HashMap<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);

            JsonNode wowAccountsNode = jsonNode.path("wow_accounts");
            for (JsonNode accountNode : wowAccountsNode) {
                JsonNode charactersNode = accountNode.path("characters");
                for (JsonNode characterNode : charactersNode) {
                    String name = characterNode.path("name").asText();
                    String server = characterNode.path("realm").path("name").asText();
                    characterMap.put(name, server);
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return characterMap;
    }
}

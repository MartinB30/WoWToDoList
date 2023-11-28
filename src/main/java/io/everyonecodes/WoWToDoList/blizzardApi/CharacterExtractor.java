package io.everyonecodes.WoWToDoList.blizzardApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CharacterExtractor {

    public Map<String, String> extractCharacters(String json) {
        Map<String, String> characterMap = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode wowAccountsNode = rootNode.path("wow_accounts");
            if (wowAccountsNode.isArray()) {
                for (JsonNode accountNode : wowAccountsNode) {
                    JsonNode charactersNode = accountNode.path("characters");
                    if (charactersNode.isArray()) {
                        for (JsonNode characterNode : charactersNode) {
                            String name = characterNode.path("name").asText();
                            String realmName = characterNode.path("realm").path("name").asText();
                            characterMap.put(name, realmName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterMap;
    }
}

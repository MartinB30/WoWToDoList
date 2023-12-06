package io.everyonecodes.WoWToDoList.blizzardApi;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/blizzard")
public class BlizzardController {

    private final BlizzardService blizzardService;

    public BlizzardController(BlizzardService blizzardService) {
        this.blizzardService = blizzardService;
    }

    @GetMapping("/profile")
    Map<String, Object> getBlizzardProfile(Authentication authentication) {

        String responseBody = blizzardService.profileInformation(authentication);
        return Collections.singletonMap("profile", responseBody);
    }
}

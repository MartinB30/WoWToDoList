package io.everyonecodes.WoWToDoList.blizzardApi;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/blizzard")
public class BlizzardController {

    private final BlizzardService blizzardApiService;
    private final OAuth2AuthorizedClientService authorizedClientService;


    public BlizzardController(BlizzardService blizzardApiService, OAuth2AuthorizedClientService authorizedClientService) {
        this.blizzardApiService = blizzardApiService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/profile")
    Map<String, String> getBlizzardProfile(Authentication authentication) {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        return blizzardApiService.profileRequest(authentication);
    }
}

package io.everyonecodes.WoWToDoList.blizzardApi;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;


@Service
public class BlizzardService {

    private final CharacterExtractor characterExtractor;
    private final OAuth2AuthorizedClientService authorizedClientService;


    public BlizzardService(CharacterExtractor characterExtractor, OAuth2AuthorizedClientService authorizedClientService) {
        this.characterExtractor = characterExtractor;
        this.authorizedClientService = authorizedClientService;
    }

    public Map<String, String> profileRequest(Authentication authentication) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        String profileUrl = "https://{region}.api.blizzard.com/profile/user/wow";
        WebClient webClient = WebClient.builder()
                .baseUrl(profileUrl.replace("{region}", "eu") + "?locale=en_US")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .defaultHeader("Battlenet-Namespace", "profile-classic1x-eu")
                .build();

        String responseBody = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return characterExtractor.extractCharacters(responseBody);
    }
}
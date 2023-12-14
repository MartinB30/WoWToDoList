package io.everyonecodes.WoWToDoList.blizzardApi;

import io.everyonecodes.WoWToDoList.blizzardApi.dto.CharacterExtractorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;


@Service
public class BlizzardService {

    private static final String REGION = "eu";
    private static final String LOCALE = "?locale=en_US";

    private final WebClient webClient;
    private final CharacterExtractor characterExtractor;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final CharacterExtractorDTO characterExtractorDTO;

    public BlizzardService(WebClient webClient, CharacterExtractor characterExtractor, OAuth2AuthorizedClientService authorizedClientService, CharacterExtractorDTO characterExtractorDTO) {
        this.webClient = webClient;
        this.characterExtractor = characterExtractor;
        this.authorizedClientService = authorizedClientService;
        this.characterExtractorDTO = characterExtractorDTO;
    }

    protected String getAccessToken(Authentication authentication) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        return authorizedClient.getAccessToken().getTokenValue();
    }

    //TODO remove duplicates in profileRequestOnlyNameAndServer and profileInformation
    public Map<String, String> profileRequestOnlyNameAndServer(Authentication authentication) {

        String accessToken = getAccessToken(authentication);
        String profileUrl = "https://{region}.api.blizzard.com/profile/user/wow";

        return webClient.get()
                .uri(profileUrl.replace("{region}", REGION) + LOCALE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .map(characterExtractor::extractCharactersNameAndServer)
                .block();
    }

    public String profileInformation(Authentication authentication) {

        String accessToken = getAccessToken(authentication);
        String profileUrl = "https://{region}.api.blizzard.com/profile/user/wow";

        return webClient.get()
                .uri(profileUrl.replace("{region}", REGION) + LOCALE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

//DOT experiment
//public List<String> profileRequestOnlyNameAndServer(Authentication authentication) {
//
//        String accessToken = getAccessToken(authentication);
//        String profileUrl = "https://{region}.api.blizzard.com/profile/user/wow";
//
//        return webClient.get()
//                .uri(profileUrl.replace("{region}", REGION) + LOCALE)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(characterExtractorDTO::apiMapper)
//                .block();
//    }
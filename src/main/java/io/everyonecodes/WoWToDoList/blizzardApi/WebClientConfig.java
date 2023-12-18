package io.everyonecodes.WoWToDoList.blizzardApi;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String NAME_SPACE = "profile-classic1x-eu";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://{region}.api.blizzard.com/profile/user/wow")
                .defaultHeader("Battlenet-Namespace", NAME_SPACE)
                .build();
    }
}

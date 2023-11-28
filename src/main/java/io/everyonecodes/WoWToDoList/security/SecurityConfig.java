package io.everyonecodes.WoWToDoList.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/callback").authenticated()
//                        .anyRequest().permitAll()
//                )
                .oauth2Login(oauth2login -> oauth2login
                        .defaultSuccessUrl("/api/blizzard/profile")
                        .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
                                .baseUri("/oauth2/authorization")));
        return http.build();
    }
}

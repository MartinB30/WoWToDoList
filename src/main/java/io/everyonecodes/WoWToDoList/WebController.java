package io.everyonecodes.WoWToDoList;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;


@RestController
public class WebController {

    private final CharacterService characterService;
    private final TaskService taskService;

    public WebController(CharacterService characterService, TaskService taskService) {
        this.characterService = characterService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    ModelAndView homePage() {

        ModelAndView view = new ModelAndView("index.html");
        view.addObject("character", characterService.findAll());
        return view;
    }

    @GetMapping("/characters/{id}")
    ModelAndView taskListFromCharacterId(@PathVariable Long id) {

        ModelAndView view = new ModelAndView("taskListCharacterId.html");
        view.addObject("task", taskService.findByCharacterId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
        return view;
    }


//    @GetMapping("/callback")
//    public Map<String, Object> blizzardLogin(Authentication authentication) {
//        if (authentication != null) {
//            System.out.println("Authentication Type: " + authentication.getClass().getName());
//            System.out.println("Principal: " + authentication.getPrincipal());
//
//            if (authentication instanceof OAuth2AuthenticationToken) {
//                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//                return oauthToken.getPrincipal().getAttributes();
//            } else {
//                throw new IllegalStateException("Unexpected authentication type: " + authentication.getClass());
//            }
//        } else {
//            throw new IllegalStateException("Authentication is null");
//        }
//    }


//    @GetMapping("/callback")
//    public String handleCallback(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//            System.out.println("Benutzerdetails im Callback: " + oauth2User.getAttributes());
//            return "Erfolgreicher Callback!";
//        } else {
//            // Handle den Fall, wenn die Authentifizierung fehlschlägt
//            return "Fehlerhafte Authentifizierung!";
//        }
//    }
    @GetMapping("/callback")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }
}
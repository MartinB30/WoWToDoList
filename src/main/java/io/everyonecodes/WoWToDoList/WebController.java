package io.everyonecodes.WoWToDoList;

import io.everyonecodes.WoWToDoList.blizzardApi.BlizzardService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class WebController {

    private final CharacterService characterService;
    private final TaskService taskService;

    private final BlizzardService blizzardService;

    public WebController(CharacterService characterService, TaskService taskService, BlizzardService blizzardService) {
        this.characterService = characterService;
        this.taskService = taskService;
        this.blizzardService = blizzardService;
    }

    @GetMapping("/")
    ModelAndView homePage() {

        ModelAndView view = new ModelAndView("index.html");
        view.addObject("character", characterService.findAll());
        return view;
    }

    @GetMapping("/character/{id}")
    ModelAndView taskListFromCharacterId(@PathVariable Long id) {

        ModelAndView view = new ModelAndView("taskListCharacterId.html");
        view.addObject("task", taskService.findByCharacterId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
        return view;
    }

    @GetMapping("/character/profile")
    ModelAndView profilePage(Authentication authentication) {

        ModelAndView view = new ModelAndView("profile.html");
        Map<String, String> profile = blizzardService.profileRequest(authentication);

        Map<String, String> filteredProfile = profile.entrySet().stream()
                .filter(entry -> !characterService.isCharacterInDatabase(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        view.addObject("profile", filteredProfile);

        return view;
    }
}
package io.everyonecodes.WoWToDoList;

import io.everyonecodes.WoWToDoList.blizzardApi.BlizzardService;
import io.everyonecodes.WoWToDoList.character.WarcraftCharacter;
import io.everyonecodes.WoWToDoList.character.WarcraftCharacterService;
import io.everyonecodes.WoWToDoList.task.Task;
import io.everyonecodes.WoWToDoList.task.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class WebController {

    private final WarcraftCharacterService warcraftCharacterService;
    private final TaskService taskService;
    private final BlizzardService blizzardService;

    public WebController(WarcraftCharacterService characterService, TaskService taskService, BlizzardService blizzardService) {
        this.warcraftCharacterService = characterService;
        this.taskService = taskService;
        this.blizzardService = blizzardService;
    }

    @GetMapping("/")
    ModelAndView homePage() {

        ModelAndView view = new ModelAndView("index.html");
        view.addObject("warcraftCharacter", warcraftCharacterService.findAll());
        return view;
    }

    @GetMapping("/character/{id}")
    ModelAndView taskListFromCharacterId(@PathVariable Long id) {
        ModelAndView view = new ModelAndView("taskListCharacterId.html");

        WarcraftCharacter character = warcraftCharacterService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id));
        List<Task> tasks = taskService.findByCharacterId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tasks not found for character with id " + id));

        view.addObject("character", character);
        view.addObject("tasks", tasks);

        return view;
    }

    @GetMapping("/character/profile")
    ModelAndView profilePage(Authentication authentication) {

        ModelAndView view = new ModelAndView("profile.html");
        Map<String, String> profile = blizzardService.profileRequestOnlyNameAndServer(authentication);

        Map<String, String> filteredProfile = profile.entrySet().stream()
                .filter(entry -> !warcraftCharacterService.isCharacterInDatabase(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        view.addObject("profile", filteredProfile);

        return view;
    }

//    DTO experiment
//    @GetMapping("/character/profile")
//    ModelAndView profilePage(Authentication authentication) {
//
//        ModelAndView view = new ModelAndView("profile.html");
//        List<String> profile = blizzardService.profileRequestOnlyNameAndServer(authentication);
//
//        view.addObject("profile", profile);
//
//        return view;
//    }
}
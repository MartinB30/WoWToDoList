package io.everyonecodes.WoWToDoList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
    ModelAndView taskListFromCharacterId(@PathVariable Long id){

        ModelAndView view = new ModelAndView("taskListCharacterId.html");
        view.addObject("task", taskService.findByCharacterId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id " + id)));
        return view;
    }
}

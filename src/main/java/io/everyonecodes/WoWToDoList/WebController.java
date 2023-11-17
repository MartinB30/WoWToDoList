package io.everyonecodes.WoWToDoList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}

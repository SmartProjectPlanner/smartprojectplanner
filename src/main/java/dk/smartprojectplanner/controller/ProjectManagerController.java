package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.service.ProjectManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;

    public ProjectManagerController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    @GetMapping
    public String showRegisterForm() {


    }

    @PostMapping
    public String register() {


    }
}

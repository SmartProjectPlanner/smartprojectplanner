package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.service.ProjectManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *  Handles incoming HTTP requests from client and sends back HTTP response
 */
@Controller
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;

    public ProjectManagerController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    /**
     * Show the register form
     *
     * @param model object acts like container that sends data from controller to html page
     * @return shows the sign-up html page
     */
    @GetMapping("/registerForm")
    public String showRegisterForm(Model model) {
        model.addAttribute("newAccount", new ProjectManager());
        return "sign-up";
    }

    /**
     * Receives http request from html form and sends the info the user filled out to the service class
     *
     * @param projectManager stores the info from html form in a java object
     * @return redirects user to login html page, so the user accidentally does not fill out the same info
     * in the form twice
     */
    @PostMapping("/register")
    public String register(@ModelAttribute ProjectManager projectManager) {
        projectManagerService.register(projectManager);
        return "redirect:/login";
    }
}

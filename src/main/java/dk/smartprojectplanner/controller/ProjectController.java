package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.model.Project;
import dk.smartprojectplanner.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Handles the pages for creating a project
 */
@Controller
public class ProjectController {

    // Todo: replace with the logged-in project manager from the session (#44)
    private static final int TEMP_PROJECT_MANAGER_ID = 1;

    private final ProjectService projectService;

    /**
     * Creates the controller
     *
     * @param projectService the service used to create projects
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Shows the empty form for creating a project.
     *
     * @param model the model used by the page
     * @return      the name of the create project page
     */
    @GetMapping("/projects/create")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        return "createproject";
    }


    /**
     * Creates a project from the form. If input is invalid, the user stays on
     * the form with an error message and keeps the values already typed.
     *
     * @param project   the project filled in by the user
     * @param model     the model used by the page
     * @return          the dashboard if the project was saved, otherwise the create project page
     */
    @PostMapping("/projects/create")
    public String createProject(@ModelAttribute Project project, Model model) {
        project.setProjectManagerId(TEMP_PROJECT_MANAGER_ID);
        try {
            projectService.createProject(project);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "createproject";
        }
        return "redirect:/dashboard";
    }
}

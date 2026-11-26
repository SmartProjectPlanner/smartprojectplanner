package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.service.ProjectManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;

    public ProjectManagerController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    @GetMapping("/registerForm")
    public String showRegisterForm() {

        return "1";
    }

    @PostMapping("/register")
    public String register() {

        return "2";
    }

    /**
     * Shows the login form
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    /**
     *Authenticates a project manager and stores their ID in the session
     *
     * @param email the email submitted through the login form
     * @param password the password submitted through the login form
     * @param session the session used to keep the project manager logged in
     * @param redirectAttributes the attributes available after a redirect
     * @return a redirect to the dashboard on success or login page on failure
     */
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ProjectManager projectManager =
                    projectManagerService.login(email, password);

            session.setAttribute("userId", projectManager.getId());

            return "redirect:/dashboard";
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", exception.getMessage()
            );
            return "redirect:/login";
        }
    }

    /**
     * Logs out the project manager and ends the current session
     *
     * @param session the current user's session
     * @param redirectAttributes attributes available after the redirect
     * @return a redirect to the login page
     */
    @PostMapping("/logout")
    public String logout(
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        session.invalidate();

        redirectAttributes.addFlashAttribute(
                "successMessage", "You have been logged out"
        );
        return "redirect:/login";
    }
}

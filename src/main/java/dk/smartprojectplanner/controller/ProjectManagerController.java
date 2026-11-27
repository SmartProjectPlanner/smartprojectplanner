package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.service.ProjectManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *  Handles incoming HTTP requests from client and sends back HTTP response
 */
@Controller
public class ProjectManagerController {
    //mega nooobbb

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

    /**
     * Shows the login form
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    /**
     * Shows the dashboard for an authenticated project manager
     *
     * @return the name of the dashboard view
     */
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

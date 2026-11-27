package dk.smartprojectplanner.service;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.repository.ProjectManagerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handles business logic for registration and authentication projectmanagers
 */
@Service
public class ProjectManagerService {

    private final ProjectManagerRepository projectManagerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Creates the service with access to project manager data
     *
     * @param projectManagerRepository repository to access project manager data
     */
    public ProjectManagerService(ProjectManagerRepository projectManagerRepository) {
        this.projectManagerRepository = projectManagerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void register() {

    }

    private void validateUser() {

    }

    /**
     * Authenticates a project manager using an email and password
     *
     * @param email email form user input
     * @param password plain-text password from user input
     * @return the authenticated project manager
     * @throws IllegalArgumentException if the login-information is incorrect
     */
    public ProjectManager login(String email, String password) {
        ProjectManager projectManager = projectManagerRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password")
                );
        if (!passwordEncoder.matches(
                password,
                projectManager.getPassword()
        )) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return projectManager;
    }

}

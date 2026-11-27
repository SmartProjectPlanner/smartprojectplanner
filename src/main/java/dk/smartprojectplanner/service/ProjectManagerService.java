package dk.smartprojectplanner.service;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.repository.ProjectManagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handles business logic for registration and authentication projectmanagers
 */
@Service
public class ProjectManagerService {

    private final ProjectManagerRepository projectManagerRepository;
    private final PasswordEncoder passwordEncoder;

    public ProjectManagerService(ProjectManagerRepository projectManagerRepository, PasswordEncoder passwordEncoder) {
        this.projectManagerRepository = projectManagerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Receives the object from controller class, uses validateUser method
     *
     * Uses passwordEncoder.encode to hash the password to the database
     *
     * calls the save method in repository
     *
     * @param projectManager object variable from controller
     */
    public void register(ProjectManager projectManager) {
        validateUser(projectManager);

        String HashedPassword = passwordEncoder.encode(projectManager.getPassword());

        projectManagerRepository.save(projectManager, HashedPassword);
    }

    /**
     * checks if name is null/blank and checks if email is null or does not contain (@)
     *
     * @param projectManager object variable
     * @throws IllegalArgumentException name can not be blank
     * @throws IllegalArgumentException email is invalid
     */
    private void validateUser(ProjectManager projectManager) {
        if (projectManager.getName() == null || projectManager.getName().isBlank()) {
            throw new IllegalArgumentException("Name can not be blank");

        } else if (projectManager.getEmail() == null || !projectManager.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email is invalid");
        }
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

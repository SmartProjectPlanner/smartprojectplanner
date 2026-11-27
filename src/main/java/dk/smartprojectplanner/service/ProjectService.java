package dk.smartprojectplanner.service;

import dk.smartprojectplanner.model.Project;
import dk.smartprojectplanner.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for projects. Validates input before a project is saved.
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * Creates the service.
     *
     * @param projectRepository the repository used to save and find projects
     */
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Validates and saves a new project.
     *
     * @param project the project to create
     * @throws IllegalArgumentException if the project has invalid values
     */
    public void createProject(Project project) {
        validate(project);
        projectRepository.save(project);
    }

    /**
     * Finds all projects owned by the given project manager.
     *
     * @param projectManagerId  the id of the project manager
     * @return                  the projects of that manager or an empty list if there are none
     */
    public List<Project> getProjectsForManager(int projectManagerId) {
        return projectRepository.findByProjectManagerId(projectManagerId);
    }

    /**
     * Checks the project and throws an IllegalArgumentException with a message
     * for the user if something is invalid
     *
     * @param project the project to check
     */
    private void validate(Project project) {
        if (project.getTitle() == null || project.getTitle().isBlank()) {
            throw new IllegalArgumentException("Titel skal være udfyldt.");
        }
        if (project.getTitle().length() > 100) {
            throw new IllegalArgumentException("Titlen må højst være 100 tegn.");
        }
        if (project.getDescription() != null && project.getDescription().length() > 250) {
            throw new IllegalArgumentException("Beskrivelsen må højst være 250 tegn.");
        }
        if (project.getStartDate() == null) {
            throw new IllegalArgumentException("Startdato skal være udfyldt.");
        }
        if (project.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline skal være udfyldt.");
        }
        if (project.getDeadline().isBefore(project.getStartDate())) {
            throw new IllegalArgumentException("Deadline kan ikke være før startdatoen.");
        }
    }
}

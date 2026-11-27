package dk.smartprojectplanner.service;

import dk.smartprojectplanner.model.Project;
import dk.smartprojectplanner.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * Unit tests for ProjectService. The repository is mocked, so no database is needed.
 */
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    /**
     * Builds a project that passes all validation rules.
     *
     * @return a valid project
     */
    private Project validProject() {
        Project project = new Project();
        project.setProjectManagerId(1);
        project.setTitle("My project");
        project.setDescription("En beskrivelse");
        project.setStartDate(LocalDate.of(2026, 12, 1));
        project.setDeadline(LocalDate.of(2026, 12, 23));
        return project;
    }

    /**
     * A valid project is passed on to the repository and saved.
     */
    @Test
    void createProject_validProject_savesProject() {
        Project project = validProject();

        projectService.createProject(project);

        verify(projectRepository).save(project);
    }

    /**
     * The description is optional, so a project without one is still saved.
     */
    @Test
    void createProject_descriptionIsOptional_savesProject() {
        Project project = validProject();
        project.setDescription(null);

        projectService.createProject(project);

        verify(projectRepository).save(project);
    }

    /**
     * A blank title is rejected and nothing is saved.
     */
    @Test
    void createProject_emptyTitle_throwsAndDoesNotSave() {
        Project project = validProject();
        project.setTitle("  ");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> projectService.createProject(project));

        assertEquals("Titel skal være udfyldt.", e.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    /**
     * A title longer than 100 characters is rejected and nothing is saved.
     */
    @Test
    void createProject_titleTooLong_ThrowsAndDoesNotSave() {
        Project project = validProject();
        project.setTitle("a".repeat(101));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> projectService.createProject(project));

        assertEquals("Titlen må højst være 100 tegn.", e.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    /**
     * A description longer than 250 characters is rejected and nothing is saved.
     */
    @Test
    void createProject_descriptionTooLong_ThrowsAndDoesNotSave() {
        Project project = validProject();
        project.setDescription("a".repeat(251));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> projectService.createProject(project));

        assertEquals("Beskrivelsen må højst være 250 tegn.", e.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    /**
     * A missing start date is rejected and nothing is saved.
     */
    @Test
    void createProject_missingStartDate_throwsAndDoesNotSave() {
        Project project = validProject();
        project.setStartDate(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> projectService.createProject(project));

        assertEquals("Startdato skal være udfyldt.", e.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    /**
     * A missing deadline is rejected and nothing is saved.
     */
    @Test
    void createProject_missingDeadline_throwsAndDoesNotSave() {
        Project project = validProject();
        project.setDeadline(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> projectService.createProject(project));

        assertEquals("Deadline skal være udfyldt.", e.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    /**
     * A deadline before the startdate is rejected and nothing is saved.
     */
    @Test
    void createProject_deadlineBeforeStartDAte_throwsAndDoesNotSave() {
        Project project = validProject();
        project.setStartDate(LocalDate.of(2026, 12, 31));
        project.setDeadline(LocalDate.of(2026, 12, 5));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> projectService.createProject(project));

        assertEquals("Deadline kan ikke være før startdatoen.", e.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }
}

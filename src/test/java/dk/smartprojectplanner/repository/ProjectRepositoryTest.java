package dk.smartprojectplanner.repository;



import dk.smartprojectplanner.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/**
 * Integration tests for ProjectRepository against an in-memory H2 database.
 * The schema and two project managers are created by h2init.sql before each test.
 */
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;


    /**
     * Builds a project owned by the given project manager.
     *
     * @param projectManagerId the id of the owning project manager
     * @return                  a project ready to be saved
     */
    private Project newProject(int projectManagerId) {
        Project project = new Project();
        project.setProjectManagerId(projectManagerId);
        project.setTitle("Mit Projekt");
        project.setDescription("En Beskrivelse");
        project.setStartDate(LocalDate.of(2026, 12, 1));
        project.setDeadline(LocalDate.of(2026, 12, 23));
        return project;
    }

    /**
     * A saved project can be read back with the same values, and end date and share token are null.
     */
    @Test
    void save_validProject_canBeFoundAgain() {
        int rows = projectRepository.save(newProject(1));

        List<Project> projects = projectRepository.findByProjectManagerId(1);

        assertThat(rows).isEqualTo(1);
        assertThat(projects).hasSize(1);
        Project saved = projects.get(0);
        assertThat(saved.getTitle()).isEqualTo("Mit Projekt");
        assertThat(saved.getDescription()).isEqualTo("En Beskrivelse");
        assertThat(saved.getStartDate()).isEqualTo(LocalDate.of(2026, 12, 1));
        assertThat(saved.getDeadline()).isEqualTo(LocalDate.of(2026, 12, 23));
        assertThat(saved.getProjectManagerId()).isEqualTo(1);
        assertThat(saved.getEndDate()).isNull();
        assertThat(saved.getShareToken()).isNull();
    }

    @Test
    void findByProjectManagerId_otherManager_returnsOnlyOwnProjects() {
        projectRepository.save(newProject(1));

        assertThat(projectRepository.findByProjectManagerId(2)).isEmpty();
    }
}

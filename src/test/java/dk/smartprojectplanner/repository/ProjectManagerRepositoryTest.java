package dk.smartprojectplanner.repository;

import dk.smartprojectplanner.model.ProjectManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class ProjectManagerRepositoryTest {

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    //Tests save method and tests findById method
    // Have to create 2 users or more
    @Test
    void saveRegisteredProjectManagerAndFindById() {
        ProjectManager newProjectManager = new ProjectManager(3,"Test Manager", "sdsdds@test.dk", "test123");

        projectManagerRepository.save(newProjectManager, "hashedPassword");

        ProjectManager found = projectManagerRepository.findById(3);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Manager");
        assertThat(found.getEmail()).isEqualTo("sdsdds@test.dk");

    }

    @Test
    void projectManagerEmailAlreadyExists() {
        ProjectManager existingProjectManager = new ProjectManager(1,"Daniel", "daniel@gmail.com", "1234");
        ProjectManager newProjectManager = new ProjectManager(2,"Simon", "daniel@gmail.com", "2345");

        projectManagerRepository.save(existingProjectManager, "hashedPassword");

        assertThatThrownBy(() -> projectManagerRepository.emailExists(newProjectManager))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exists");
    }
}

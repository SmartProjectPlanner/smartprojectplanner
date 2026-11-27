package dk.smartprojectplanner.projectManagerTest;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.repository.ProjectManagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Statement;
import java.util.List;

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
        ProjectManager newProjectManager = new ProjectManager(1,"Malte", "m@gmail.com", "password");
        ProjectManager newProjectManager2 = new ProjectManager(2,"Malte2", "m2@gmail.com", "2password");

        projectManagerRepository.save(newProjectManager, "hashedPassword");
        projectManagerRepository.save(newProjectManager2, "2hashedPassword");


        ProjectManager found = projectManagerRepository.findById(1);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Malte");
        assertThat(found.getEmail()).isEqualTo("m@gmail.com");

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

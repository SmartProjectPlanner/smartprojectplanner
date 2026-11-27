package dk.smartprojectplanner.projectManagerTest;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.repository.ProjectManagerRepository;
import dk.smartprojectplanner.service.ProjectManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProjectManagerServiceTest {

    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    ProjectManagerRepository repository;

    @InjectMocks
    ProjectManagerService service;

    @Test
    void savesRegisteredUserAndHashedPassword() {

        ProjectManager newProjectManager = new ProjectManager(1, "Carl", "carl@gmail.com", "321");

        service.register(newProjectManager);

        ArgumentCaptor<ProjectManager> captor = forClass(ProjectManager.class);
        var hashCaptor = forClass(String.class);
        verify(repository).save(captor.capture(), hashCaptor.capture());

        ProjectManager saved = captor.getValue();
        assertEquals("Carl", saved.getName());
        assertEquals("carl@gmail.com", saved.getEmail());
        assertEquals(passwordEncoder.matches("321", hashCaptor.getValue()), true);

    }

    @Test
    void registerNameIsBlank() {

        ProjectManager newProjectManager = new ProjectManager(1, "", "samuel@gmail.com", "code");

        assertThatThrownBy(() -> service.register(newProjectManager))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name can not be blank");
    }

    @Test
    void registerEmailWrongFormat() {

        ProjectManager newProjectManager = new ProjectManager(1, "simon", "simongmail.com", "denBedsteIverden");

        assertThatThrownBy(() -> service.register(newProjectManager))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email is invalid");
    }

    @Test
    void registerEmailIsBlack() {

        ProjectManager newProjectManager = new ProjectManager(1, "lauge", "", "kode");

        assertThatThrownBy(() -> service.register(newProjectManager))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email is invalid");

    }

}

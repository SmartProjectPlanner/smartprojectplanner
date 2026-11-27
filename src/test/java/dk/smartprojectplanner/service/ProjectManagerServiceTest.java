package dk.smartprojectplanner.service;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.repository.ProjectManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectManagerServiceTest{


    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private ProjectManagerRepository projectManagerRepository;

    private ProjectManagerService projectManagerService;

    @InjectMocks
    ProjectManagerService service;

    @BeforeEach
    void setUp(){
        projectManagerService =
                new ProjectManagerService(projectManagerRepository, passwordEncoder);

        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void loginReturnsProjectManagerWhenCredentialsAreCorrect() {
        String email = "villa@denseje.dk";
        String password = "Test1234";

        ProjectManager expectedProjectManager = new ProjectManager(
                1,
                "Villa",
                email,
                passwordEncoder.encode(password)
        );

        when(projectManagerRepository.findByEmail(email))
                .thenReturn(Optional.of(expectedProjectManager));

        ProjectManager actualProjectManager = projectManagerService.login(email, password);

        assertSame(expectedProjectManager, actualProjectManager);
    }

    @Test
    void loginThrowsExceptionsWhenPasswordIsIncorrect(){
        String email = "villa@denseje.dk";
        String correctPassword = "Test1234";
        String incorrectPassword = "WrongPasswordHoney";

        ProjectManager projectManager = new ProjectManager(
                1,
                "Villa",
                email,
                passwordEncoder.encode(correctPassword)
        );

        when(projectManagerRepository.findByEmail(email))
                .thenReturn(Optional.of(projectManager));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> projectManagerService.login(email, incorrectPassword)
        );

        assertEquals(
                "Invalid email or password", exception.getMessage()
        );
    }

    @Test
    void loginThrowsExceptionWhenEmailIsUnkown(){
        String email = "villa@denmindreseje.dk";
        String password ="Test1234";

        when(projectManagerRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> projectManagerService.login(email, password)
        );

        assertEquals(
                "Invalid email or password", exception.getMessage()
        );
    }

    @Test
    void savesRegisteredUserAndHashedPassword() {

        ProjectManager newProjectManager = new ProjectManager(1, "Carl", "carl@gmail.com", "321");

        service.register(newProjectManager);

        ArgumentCaptor<ProjectManager> captor = forClass(ProjectManager.class);
        var hashCaptor = forClass(String.class);
        verify(projectManagerRepository).save(captor.capture(), hashCaptor.capture());

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



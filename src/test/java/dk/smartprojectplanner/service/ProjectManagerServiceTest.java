package dk.smartprojectplanner.service;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.repository.ProjectManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectManagerServiceTest{

    @Mock
    private ProjectManagerRepository projectManagerRepository;

    private ProjectManagerService projectManagerService;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        projectManagerService =
                new ProjectManagerService(projectManagerRepository);

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


}


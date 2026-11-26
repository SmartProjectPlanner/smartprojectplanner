package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.service.ProjectManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectManagerController.class)
    class ProjectManagerControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ProjectManagerService projectManagerService;

        @Test
    void loginRedirectsToDashboardAndStoresUserIdInSession() throws Exception{
            String email = "villa@denseje.dk";
            String password = "Test1234";

            ProjectManager projectManager = new ProjectManager(
                    1,
                    "Villa",
                    email,
                    "hashedPassword"
            );

            when(projectManagerService.login(email, password))
                    .thenReturn(projectManager);

            mockMvc.perform(post("/login")
                    .param("email", email)
                    .param("password", password))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/dashboard"))
                    .andExpect(request().sessionAttribute("userId", 1));
    }

    @Test
    void loginRedirectsToLoginPageWhenCredentialsAreIncorrect() throws Exception {
            String email = "villa@denseje.dk";
            String password = "noob1234";

            when(projectManagerService.login(email, password))
                    .thenThrow(
                            new IllegalArgumentException(
                                    "Invalid email or password"
                            )
                    );

            mockMvc.perform(post("/login")
                    .param("email", email)
                    .param("password", password))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"))
                    .andExpect(flash().attribute(
                            "errorMessage",
                            "Invalid email or password"
                    ));
    }
}

package dk.smartprojectplanner.controller;

import dk.smartprojectplanner.model.Project;
import dk.smartprojectplanner.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web layer tests for ProjectController. The service is mocked.
 */
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    /**
     * GET /projects/create shows the create project form with an empty project in the model.
     *
     * @throws Exception if the request fails
     */
    @Test
    void showCreateForm_returnsCreateProjectViewWithEmptyProject() throws Exception {
        mockMvc.perform(get("/projects/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createproject"))
                .andExpect(model().attributeExists("project"));
    }

    /**
     * POST with valid input saves the project and redirects to the dashboard.
     *
     * @throws Exception if the request fails
     */
    @Test
    void createProject_validInput_savesAndRedirectsToDashboard() throws Exception {
        mockMvc.perform(post("/projects/create")
                .param("title", "Mit projekt")
                .param("description", "En Beskrivelse")
                .param("startDate", "2026-12-05")
                .param("deadline", "2026-12-31"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    /**
     * POST with invalid input keeps the user on the form and shows the error message.
     *
     * @throws Exception if the request fails
     */
    @Test
    void createProject_invalidInput_staysOnPageWithErrorMessage() throws Exception {
        doThrow(new IllegalArgumentException("Titel skal være udfyldt."))
                .when(projectService).createProject(any(Project.class));

        mockMvc.perform(post("/projects/create")
                .param("title", "")
                .param("startDate", "2026-12-05")
                .param("deadline", "2026-12-31"))
                .andExpect(status().isOk())
                .andExpect(view().name("createproject"))
                .andExpect(model().attribute("error", "Titel skal være udfyldt."));

    }
}

package dk.smartprojectplanner.projectManagerTest;

import dk.smartprojectplanner.controller.ProjectManagerController;
import dk.smartprojectplanner.model.ProjectManager;
import dk.smartprojectplanner.service.ProjectManagerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectManagerController.class)
public class ProjectManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectManagerService projectManagerService;

    @Test
    void showRegisterForm() throws Exception {

        mockMvc.perform(get("/registerForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up"))
                .andExpect(model().attributeExists("newAccount"))
                .andExpect(model().attribute("newAccount", instanceOf(ProjectManager.class)));
    }

    @Test
    void register() throws Exception {

        mockMvc.perform(post("/register")
                .param("name", "simon")
                .param("email", "simon@gmail.com")
                .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<ProjectManager> captor = ArgumentCaptor.forClass(ProjectManager.class);
        verify(projectManagerService).register(captor.capture());

        ProjectManager saved = captor.getValue();
        assertEquals("simon", saved.getName());
        assertEquals("simon@gmail.com", saved.getEmail());
        assertEquals("password", saved.getPassword());
    }


}

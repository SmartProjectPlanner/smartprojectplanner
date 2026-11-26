package dk.smartprojectplanner.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    //MockMvc laver "falske" HTTP-requests til controlleren sådan at vi kan køre tests uden at serveren skal startes op.
    @Autowired
    private MockMvc mockMvc;

    //Testen benytter showHomePage() controlleren og tjekker at vi får en HTTP status OK
    // og at vi viewer HTML filen "index" når der sendes en getRequest til endpointet ("/").
    @Test
    void showHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
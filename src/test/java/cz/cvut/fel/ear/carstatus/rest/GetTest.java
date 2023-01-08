package cz.cvut.fel.ear.carstatus.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
class GetTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveAllTyresRespondsWithTyres() throws Exception {
        this.mockMvc.perform(get("/rest/tyre/").with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void retrieveAllRoadsRespondsWithRoads() throws Exception {
        this.mockMvc.perform(get("/rest/road/").with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void retrieveBatteriesRespondsWithBatteries() throws Exception {
        this.mockMvc.perform(get("/rest/battery/").with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    void retrieveCurrentUserRespondsWithUser() throws Exception {
        this.mockMvc.perform(get("/rest/user/current/").with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Current logged user: driver@fel.ear.cvut.cz"));
    }
    @Test
    void retrieveUnexistingBatteryRespondsWithNotFound() throws Exception {
        this.mockMvc.perform(get("/rest/battery/250").with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
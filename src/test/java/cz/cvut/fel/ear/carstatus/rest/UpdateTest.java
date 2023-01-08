package cz.cvut.fel.ear.carstatus.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.ear.carstatus.config.AppConfig;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UpdateTest {

    ObjectMapper objectMapper = new AppConfig().objectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BatteryService batteryService;

    @Test
    @Transactional
    public void updateExistingBatteryThrowsException() throws Exception {
        Battery toUpdate = batteryService.getCurrentBattery();
        Random rnd = new Random();

        assertThrows(AssertionError.class, () -> mockMvc.perform(put("/rest/battery/").content(toJson(toUpdate)).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user("admin@fel.ear.cvut.cz").password("admin").roles("ADMIN")))
                .andExpect(status().isUnauthorized()));

    }
    @Test
    @Transactional
    public void updateTyresInflatesTyres() throws Exception {
        mockMvc.perform(put("/rest/tyre/inflate")
                        .with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void updateTyresWithAdminRoleRespondsWithForbidden() throws Exception {
        mockMvc.perform(put("/rest/tyre/inflate")
                        .with(user("admin@fel.ear.cvut.cz").password("admin").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}

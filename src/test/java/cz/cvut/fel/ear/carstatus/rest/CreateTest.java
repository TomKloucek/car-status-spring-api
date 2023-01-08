package cz.cvut.fel.ear.carstatus.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.ear.carstatus.config.AppConfig;
import cz.cvut.fel.ear.carstatus.exception.EarException;
import cz.cvut.fel.ear.carstatus.exception.PersistenceException;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import org.checkerframework.checker.units.qual.A;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateTest {

    ObjectMapper objectMapper = new AppConfig().objectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void createBatteryCreatesBatteryUsingService() throws Exception {
        final Battery toCreate = new Battery();
        Random rnd = new Random();
        int id = rnd.nextInt();
        toCreate.setId(Math.abs(id));
        toCreate.setCondition(100);
        toCreate.setCapacity(100);
        toCreate.setInUsage(false);

        mockMvc.perform(post("/rest/battery/").content(toJson(toCreate)).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andExpect(status().isCreated());
        }

    @Test
    @Transactional
    public void createTyreWithPointlessPressureThrowsError() throws Exception {
        final Tyre toCreate = new Tyre();
        Random rnd = new Random();
        int id = rnd.nextInt();
        toCreate.setId(Math.abs(id));
        toCreate.setCondition(100);
        toCreate.setPressure(100);
        toCreate.setInUsage(false);

        assertThrows(AssertionError.class, () -> mockMvc.perform(post("/rest/tyre/").content(toJson(toCreate)).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andExpect(status().isCreated()));
    }

    @Test
    @Transactional
    public void createTyreWithoutAuthorizationRespondsWithUnauthorized() throws Exception {
        final Tyre toCreate = new Tyre();
        Random rnd = new Random();
        int id = rnd.nextInt();
        toCreate.setId(Math.abs(id));
        toCreate.setCondition(100);
        toCreate.setPressure(100);
        toCreate.setInUsage(false);

       mockMvc.perform(post("/rest/tyre/").content(toJson(toCreate)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void performCommandCreatesRoad() throws Exception {

        mockMvc.perform(post("/rest/command/execute/")
                        .with(user("admin@fel.ear.cvut.cz").password("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }
    String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}

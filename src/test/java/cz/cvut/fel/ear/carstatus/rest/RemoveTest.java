package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.service.BatteryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RemoveTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BatteryService batteryServiceMock;

    @Test
    void removeBatteryRespondsUnauthorizedToNotLoggedUser() throws Exception {
        mockMvc.perform(delete("/rest/tyre/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verify(batteryServiceMock, never()).deleteBattery(any());
    }

    @Test
    void removeBatteryRespondsForbiddenToNotAdmin() throws Exception {
        mockMvc.perform(delete("/rest/battery/1").with(user("driver@fel.ear.cvut.cz").password("driver").roles("DRIVER")))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(batteryServiceMock, never()).deleteBattery(any());
    }
}

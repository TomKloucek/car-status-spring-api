package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;
import cz.cvut.fel.ear.carstatus.TestSecurityConfig;
import cz.cvut.fel.ear.carstatus.commands.GenerateDriverCommand;
import cz.cvut.fel.ear.carstatus.commands.GenerateRoadCommand;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.model.Role;
import cz.cvut.fel.ear.carstatus.security.WebSecurityConfig;
import cz.cvut.fel.ear.carstatus.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@ComponentScan(basePackageClasses = CarstatusApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
@Import(TestSecurityConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@AutoConfigureDataJpa
class SimulationTest {

    @Autowired
    private RoadTripService roadTripService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RoadService roadService;
    @Autowired
    private BatteryService batteryService;

    @Autowired
    CarStateService carStateService;

    @Autowired
    TyreService tyreservice;

    @Autowired
    SimulationService simulation;


    @Test
    void basicSimulationTest() {
        int initialSize = roadTripService.findAll().size();
        Random random = new Random();
        Battery battery = new Battery();
        battery.setCapacity(random.nextInt());
        battery.setCondition(random.nextInt());
        battery.setInUsage(true);
        battery.setId(Math.abs(random.nextInt()));
        batteryService.createNewBattery(battery);
        batteryService.changeCurrentBattery(battery);
        generateDriver();
        generateRoads();
        simulation.generateOneRoadTrip();
        assertEquals(initialSize+1,roadTripService.findAll().size());
    }

    public void generateDriver() {
        Driver user = new Driver();
        user.setUsername("pepik");
        user.setFirstName("System");
        user.setBirthDate(new Date());
        user.setLastName("Administrator");
        user.setPassword("adm1n");
        user.setRole(Role.DRIVER);
        driverService.persist(user);
    }

    public void generateRoads() {
        Road r1 = new Road();
        r1.setLength(210);
        r1.setStartingPoint("Praha");
        r1.setEndPoint("Brno");
        roadService.persist(r1);
        Road r2 = new Road();
        r2.setLength(210);
        r2.setEndPoint("Praha");
        r2.setStartingPoint("Brno");
        roadService.persist(r2);
    }
}
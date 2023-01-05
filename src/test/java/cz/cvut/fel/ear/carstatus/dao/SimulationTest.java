package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.model.Role;
import cz.cvut.fel.ear.carstatus.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ComponentScan(basePackageClasses = CarstatusApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class SimulationTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RoadTripService roadTripService;

    @Autowired
    private RoadPathService roadPathService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private LiquidService liquidService;

    @Autowired
    private RoadService roadService;

    @Autowired
    private BatteryService batteryService;
    @Test
    public void basicSimulationTest() {
        Simulation simulation = new Simulation(driverService,roadTripService,roadPathService,liquidService,roadService,batteryService);
        int initialSize = roadTripService.findAll().size();
        generateDriver();
        generateRoads();
        simulation.generateOneRoadTrip();
        assertEquals(initialSize+1,roadTripService.findAll().size());
    }

    public void generateDriver() {
        Driver user = new Driver();
        user.setUsername("driver");
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
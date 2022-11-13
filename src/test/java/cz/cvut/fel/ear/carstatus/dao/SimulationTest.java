package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

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
    @Test
    public void basicSimulationTest() {
        Simulation simulation = new Simulation(driverService,roadTripService,roadPathService,liquidService,roadService);
        int initialSize = roadTripService.findAll().size();
        simulation.generateOneRoadTrip();
        assertEquals(initialSize+1,roadTripService.findAll().size());
    }
}
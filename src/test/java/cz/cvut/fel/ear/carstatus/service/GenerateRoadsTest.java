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
import org.springframework.transaction.annotation.Transactional;

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
class GenerateRoadsTest {



    @Autowired
    private RoadService roadService;

    @Autowired
    TyreService tyreservice;

    @Test
    @Transactional
    public void generateRoads() {
        Random rnd = new Random();
        Road r1 = new Road();
        r1.setId(Math.abs(rnd.nextInt()));
        r1.setLength(210);
        r1.setStartingPoint("Praha");
        r1.setEndPoint("Brno");
        roadService.persist(r1);
        final Road firstGeneratedRoadResult = roadService.find(r1.getId());
        Road r2 = new Road();
        r2.setId(Math.abs(rnd.nextInt()));
        r2.setLength(210);
        r2.setEndPoint("Praha");
        r2.setStartingPoint("Brno");
        roadService.persist(r2);
        final Road secondGeneratedRoadResult = roadService.find(r2.getId());

        assertNotNull(firstGeneratedRoadResult);
        assertNotNull(secondGeneratedRoadResult);
        assertEquals(r1, firstGeneratedRoadResult);
        assertEquals(r2, secondGeneratedRoadResult);
    }
}
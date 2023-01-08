package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;

import cz.cvut.fel.ear.carstatus.TestSecurityConfig;
import cz.cvut.fel.ear.carstatus.exception.PersistenceException;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import cz.cvut.fel.ear.carstatus.security.WebSecurityConfig;
import cz.cvut.fel.ear.carstatus.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

// For explanatory comments, see ProductDaoTest
@DataJpaTest
@ComponentScan(basePackageClasses = CarstatusApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BaseDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BatteryDao sut;
    @Test
    @Transactional
    void persistSavesSpecifiedInstance() {
        final Battery battery = generateBattery();
        em.persist(battery);

        final Battery result = sut.find(battery.getId());
        assertNotNull(result);
        assertEquals(battery.getId(), result.getId());
        assertEquals(battery.getCondition(), result.getCondition());
        assertEquals(battery.getCapacity(), result.getCapacity());
    }

    @Transactional
    Battery generateBattery() {
        Random random = new Random();
        Battery battery = new Battery();
        battery.setCapacity(random.nextInt());
        battery.setCondition(random.nextInt());
        battery.setInUsage(true);
        battery.setId(Math.abs(random.nextInt()));
        return battery;
    }

    @Transactional
    @Test
    void findAllRetrievesAllInstancesOfType() {
        final int beforeAdditionCount = sut.findAll().size();
        final Battery firstBattery = generateBattery();
        em.persist(firstBattery);
        final Battery secondBattery = generateBattery();
        em.persist(secondBattery);
        final int numberOfAddedBatteries = 2;

        final List<Battery> result = sut.findAll();
        assertEquals(beforeAdditionCount+numberOfAddedBatteries, result.size());
        System.out.println(result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(firstBattery.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(secondBattery.getId())));
    }

    @Transactional
    @Test
    void updateUpdatesExistingInstance() {
        final Battery battery = generateBattery();
        em.persist(battery);

        final Battery update = new Battery();
        update.setId(battery.getId());
        final int newCapacity = 77;
        update.setCapacity(newCapacity);
        sut.update(update);

        final Battery result = sut.find(battery.getId());
        assertNotNull(result);
        assertEquals(battery.getCapacity(), result.getCapacity());
    }

    @Transactional
    @Test
    void removeRemovesSpecifiedInstance() {
        final Battery battery = generateBattery();
        em.persist(battery);
        assertNotNull(em.find(Battery.class, battery.getId()));
        em.detach(battery);

        sut.remove(battery);
        assertNull(em.find(Battery.class, battery.getId()));
    }

    @Transactional
    @Test
    void removeDoesNothingWhenInstanceDoesNotExist() {
        final Battery battery = generateBattery();
        battery.setId(123);
        assertNull(em.find(Battery.class, battery.getId()));

        sut.remove(battery);
        assertNull(em.find(Battery.class, battery.getId()));
    }

    @Transactional
    @Test
    void exceptionOnPersistInWrappedInPersistenceException() {
        Battery battery = generateBattery();
        em.persist(battery);
        em.remove(battery);
        battery = sut.find(battery.getId());
        Battery finalBattery = battery;
        assertThrows(PersistenceException.class, () -> sut.update(finalBattery));
    }

    @Transactional
    @Test
    void existsReturnsTrueForExistingIdentifier() {
        final Battery battery = generateBattery();
        em.persist(battery);
        assertTrue(sut.exists(battery.getId()));
        assertFalse(sut.exists(-1));
    }

}

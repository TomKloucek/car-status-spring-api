package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;

import cz.cvut.fel.ear.carstatus.exception.PersistenceException;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import cz.cvut.fel.ear.carstatus.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

// For explanatory comments, see ProductDaoTest
@DataJpaTest
@ComponentScan(basePackageClasses = CarstatusApplication.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = TestConfiguration.class)})
public class BaseDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BatteryDao sut;
    @Test
    public void persistSavesSpecifiedInstance() {
        final Battery battery = generateBattery();
        sut.persist(battery);
        assertNotNull(battery.getId());

        final Battery result = em.find(Battery.class, battery.getId());
        assertNotNull(result);
        assertEquals(battery.getId(), result.getId());
        assertEquals(battery.getCondition(), result.getCondition());
        assertEquals(battery.getCapacity(), result.getCapacity());
    }

    private static Battery generateBattery() {
        Random random = new Random();
        final Battery battery = new Battery();
        battery.setCapacity(random.nextInt());
        battery.setCondition(random.nextInt());
        battery.setInUsage(true);
        return battery;
    }

    @Test
    public void findAllRetrievesAllInstancesOfType() {
        final Battery firstBattery = generateBattery();
        em.persistAndFlush(firstBattery);
        final Battery secondBattery = generateBattery();
        em.persistAndFlush(secondBattery);

        final List<Battery> result = sut.findAll();
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(firstBattery.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(secondBattery.getId())));
    }
    @Test
    public void updateUpdatesExistingInstance() {
        final Battery battery = generateBattery();
        em.persistAndFlush(battery);

        final Battery update = new Battery();
        update.setId(battery.getId());
        final int newCapacity = 77;
        update.setCapacity(newCapacity);
        sut.update(update);

        final Battery result = sut.find(battery.getId());
        assertNotNull(result);
        assertEquals(battery.getCapacity(), result.getCapacity());
    }

    @Test
    public void removeRemovesSpecifiedInstance() {
        final Battery battery = generateBattery();
        em.persistAndFlush(battery);
        assertNotNull(em.find(Battery.class, battery.getId()));
        em.detach(battery);

        sut.remove(battery);
        assertNull(em.find(Battery.class, battery.getId()));
    }

    @Test
    public void removeDoesNothingWhenInstanceDoesNotExist() {
        final Battery battery = generateBattery();
        battery.setId(123);
        assertNull(em.find(Battery.class, battery.getId()));

        sut.remove(battery);
        assertNull(em.find(Battery.class, battery.getId()));
    }

    @Test
    public void exceptionOnPersistInWrappedInPersistenceException() {
        final Battery battery = generateBattery();
        em.persistAndFlush(battery);
        em.remove(battery);
        assertThrows(PersistenceException.class, () -> sut.update(battery));
    }

    @Test
    public void existsReturnsTrueForExistingIdentifier() {
        final Battery battery = generateBattery();
        em.persistAndFlush(battery);
        assertTrue(sut.exists(battery.getId()));
        assertFalse(sut.exists(-1));
    }

}

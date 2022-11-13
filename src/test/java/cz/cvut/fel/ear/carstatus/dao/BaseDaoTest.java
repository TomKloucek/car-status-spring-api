package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.CarstatusApplication;

import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

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
        final Battery battery = new Battery();
        battery.setCapacity(100);
        battery.setCondition(97);
        battery.setInUsage(true);
        return battery;
    }
}

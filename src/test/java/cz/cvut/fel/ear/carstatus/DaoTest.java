package cz.cvut.fel.ear.carstatus;

import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.model.Battery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ComponentScan(basePackageClasses = CarstatusApplication.class)
public class DaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BatteryDao sut;

    @Test

    public void persistSavesSpecifiedInstance() {
        final Battery battery = new Battery();
        battery.setInUsage(true);
        battery.setCondition(100);
        battery.setCapacity(80);
        sut.persist(battery);
        assertNotNull(battery.getId());

        final Battery result = em.find(Battery.class, battery.getId());
        assertNotNull(result);
        assertEquals(battery.getId(), result.getId());
        assertEquals(battery.getInUsage(), result.getInUsage());
    }
}

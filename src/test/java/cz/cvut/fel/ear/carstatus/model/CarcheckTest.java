package cz.cvut.fel.ear.carstatus.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarcheckTest {

    @Test
    public void carCheckIsAppointedToMechanic() {
        final Carcheck carcheck = new Carcheck();
        final Mechanic mechanic = new Mechanic();
        final Mechanic mechanic1 = new Mechanic();
        final java.sql.Date dateOfCheck = java.sql.Date.valueOf("2015-03-31");

        carcheck.setCheckDate(dateOfCheck);
        carcheck.setMechanic(mechanic);

        assertNotNull(carcheck);
        assertEquals(carcheck.getMechanic(), mechanic);
        assertEquals(carcheck.getCheckDate().toString(), "2015-03-31");
        assertNotEquals(carcheck.getMechanic(), mechanic1);
    }
}

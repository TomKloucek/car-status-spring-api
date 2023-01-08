package cz.cvut.fel.ear.carstatus.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CarcheckTest {

    @Test
    void carCheckIsAppointedToMechanic() {
        final Carcheck carcheck = new Carcheck();
        final Mechanic mechanic = new Mechanic();
        final Mechanic mechanic1 = new Mechanic();
        final java.sql.Date dateOfCheck = java.sql.Date.valueOf("2015-03-31");

        carcheck.setCheckDate(dateOfCheck);
        carcheck.setMechanic(mechanic);

        assertNotNull(carcheck);
        assertEquals(carcheck.getMechanic(), mechanic);
        assertEquals("2015-03-31", carcheck.getCheckDate().toString());
        assertNotEquals(carcheck.getMechanic(), mechanic1);
    }
}

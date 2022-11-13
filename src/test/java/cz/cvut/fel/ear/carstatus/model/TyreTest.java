package cz.cvut.fel.ear.carstatus.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TyreTest {
    @Test
    public void tyreBlowsDownAsItRuns() {
        final Tyre tyre = new Tyre();
        final Road road = new Road();
        Random random = new Random();
        final int numberOfKilometers = 150;
        road.setLength(numberOfKilometers);
        final double pressureAtStart = 6.5;
        tyre.setPressure(pressureAtStart);
        double totalBlownAmmount = 0;
        for(int i = 0; i < numberOfKilometers; i++){
            double coefficientOfBlowing = 0.01;
            tyre.setPressure(tyre.getPressure()-coefficientOfBlowing);
            totalBlownAmmount += coefficientOfBlowing;
        }

        assertNotNull(tyre);
        assertEquals(Math.round(tyre.getPressure()), Math.round(pressureAtStart-totalBlownAmmount));
    }
}

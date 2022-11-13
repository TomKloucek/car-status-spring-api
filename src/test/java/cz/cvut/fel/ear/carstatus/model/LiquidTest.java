package cz.cvut.fel.ear.carstatus.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LiquidTest {
    @Test
    public void liquidIsBelowMinimalLevelReturnsTrueWhenItReachesMinLevel() {
        final Liquid testLiquid = new Liquid();
        final int initialLevel = 100;
        final int subtractedAmount = 75;
        testLiquid.setLevel(initialLevel);
        testLiquid.setMinLevel(25);
        testLiquid.setLevel(initialLevel-subtractedAmount);

        assertEquals(testLiquid.checkWhetherIsBelowOrAtMinLevel(), true);
    }
}

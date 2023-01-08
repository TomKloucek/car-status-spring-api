package cz.cvut.fel.ear.carstatus.util;

import cz.cvut.fel.ear.carstatus.model.Driver;

import java.util.Date;

public class Permissions {

    private Permissions() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean eligibleToDrive(Driver driver) {
        return Helpers.calculateAge(Helpers.convertToLocalDateViaInstant(new Date()), Helpers.convertToLocalDateViaInstant(driver.getBirthDate())) >= 18;
    }
}

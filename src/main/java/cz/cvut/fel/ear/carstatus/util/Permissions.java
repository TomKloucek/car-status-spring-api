package cz.cvut.fel.ear.carstatus.util;

import cz.cvut.fel.ear.carstatus.model.Driver;
import org.eclipse.persistence.internal.helper.Helper;

import java.util.Date;

public class Permissions {

    public static boolean eligibleToDrive(Driver driver) {
        return Helpers.calculateAge(Helpers.convertToLocalDateViaInstant(new Date()), Helpers.convertToLocalDateViaInstant(driver.getBirthDate())) >= 18;
    }
}

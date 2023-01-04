package cz.cvut.fel.ear.carstatus.util;

import cz.cvut.fel.ear.carstatus.model.Role;

public final class Constants {

    /**
     * Default user role.
     */
    public static final Role DEFAULT_ROLE = Role.DRIVER;

    public static final Integer MINIMAL_TYRE_PRESSURE = 25;
    public static final Integer MINIMAL_BATTERY_CHARGE = 15;
    public static final Integer MINIMAL_BATTERY_CONDITION = 50;
    public static final Integer NUMBER_OF_FILEACCESSES_IN_POOL = 3;

    private Constants() {
        throw new AssertionError();
    }
}

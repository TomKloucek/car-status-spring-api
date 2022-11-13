package cz.cvut.fel.ear.carstatus.util;

import cz.cvut.fel.ear.carstatus.model.Role;

public final class Constants {

    /**
     * Default user role.
     */
    public static final Role DEFAULT_ROLE = Role.DRIVER;

    private Constants() {
        throw new AssertionError();
    }
}

package cz.cvut.fel.ear.carstatus.model;

public enum Role {
    ADMIN("ROLE_ADMIN"), DRIVER("ROLE_DRIVER"), MECHANIC("ROLE_MECHANIC");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package cz.cvut.fel.ear.carstatus.dto;

public class BatteryDTO {
    private int id;
    private int capacity;
    private int condition;

    public int getId() {
        return id;
    }

    private boolean inUsage;

    public int getCapacity() {
        return capacity;
    }

    public int getCondition() {
        return condition;
    }

    public boolean isInUsage() {
        return inUsage;
    }
}

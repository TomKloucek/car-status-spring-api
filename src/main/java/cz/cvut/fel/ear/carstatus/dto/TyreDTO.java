package cz.cvut.fel.ear.carstatus.dto;

public class TyreDTO {
    private int position;
    private double pressure;
    private int condition;
    private boolean inUsage;

    public int getPosition() {
        return position;
    }

    public double getPressure() {
        return pressure;
    }

    public int getCondition() {
        return condition;
    }

    public boolean isInUsage() {
        return inUsage;
    }
}

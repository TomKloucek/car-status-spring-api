package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Tyre extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int position;

    @Basic(optional = false)
    @Column(nullable = false)
    private double pressure;


    @Basic(optional = false)
    @Column(nullable = false)
    private int condition;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean inUsage;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public boolean isInUsage() {
        return inUsage;
    }

    public void setInUsage(boolean inUsage) {
        this.inUsage = inUsage;
    }

    @Override
    public String toString() {
        return "Tyre{" +
                "position='" + position + '\'' +
                ", pressure=" + pressure +
                ", condition=" + condition +
                ", inUsage=" + inUsage +
                '}';
    }
}

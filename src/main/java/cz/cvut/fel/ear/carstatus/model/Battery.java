package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Battery extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int capacity;
    @Basic(optional = false)
    @Column(nullable = false)
    private int condition;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean inUsage;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
}

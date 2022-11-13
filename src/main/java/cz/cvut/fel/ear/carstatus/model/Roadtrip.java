package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Roadtrip extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int maxSpeed;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean withMalfunction;

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isWithMalfunction() {
        return withMalfunction;
    }

    public void setWithMalfunction(boolean withMalfunction) {
        this.withMalfunction = withMalfunction;
    }
}

package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Roadpath extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int averageSpeed;

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}

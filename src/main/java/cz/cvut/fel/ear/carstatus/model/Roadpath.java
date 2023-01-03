package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Roadpath extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int averageSpeed;

    @OneToOne
    private Roadtrip roadtrip;

    @OneToOne
    private Road road;

    public Roadtrip getRoadtrip() {
        return roadtrip;
    }

    public void setRoadtrip(Roadtrip roadtrip) {
        this.roadtrip = roadtrip;
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return "Roadpath{" +
                "averageSpeed=" + averageSpeed +
                ", road=" + road +
                '}';
    }
}

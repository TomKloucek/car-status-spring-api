package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Roadpath extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int averageSpeed;

    @JsonBackReference
    @OneToOne
    private Roadtrip roadtrip;

    @JsonBackReference
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

package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Roadtrip extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int maxSpeed;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean withMalfunction;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "roadtrip_id")
    private List<Roadpath> roadpathList;

    @ManyToOne
    private Driver driver;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Roadpath> getRoadpathList() {
        return roadpathList;
    }

    public void setRoadpathList(List<Roadpath> roadpathList) {
        this.roadpathList = roadpathList;
    }

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

    @Override
    public String toString() {
        return "Roadtrip{" +
                "maxSpeed=" + maxSpeed +
                ", withMalfunction=" + withMalfunction +
                ", roadpathList=" + roadpathList +
                ", driver=" + driver +
                '}';
    }
}

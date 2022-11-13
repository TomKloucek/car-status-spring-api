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
    @JoinColumn(name = "road_trip_id")
    private List<Roadpath> roadpathList;

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
}

package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Roadtrip extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int maxSpeed;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean withMalfunction;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date finished;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "roadtrip_id")
    @OrderBy("road.length")
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

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Roadtrip{" +
                "maxSpeed=" + maxSpeed +
                ", withMalfunction=" + withMalfunction +
                ", finished=" + finished +
                ", driver=" + driver +
                '}';
    }
}

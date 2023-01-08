package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Road extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String startingPoint;

    @Basic(optional = false)
    @Column(nullable = false)
    private String endPoint;

    @Basic(optional = false)
    @Column(nullable = false)
    private int length;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "road_id")
    private List<Roadpath> roadpathList;


    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Roadpath> getRoadpathList() {
        return roadpathList;
    }

    public void setRoadpathList(List<Roadpath> roadpathList) {
        this.roadpathList = roadpathList;
    }

    @Override
    public String toString() {
        return "Road{" +
                "startingPoint='" + startingPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", length=" + length +
                ", roadpathList=" + roadpathList +
                '}';
    }
}

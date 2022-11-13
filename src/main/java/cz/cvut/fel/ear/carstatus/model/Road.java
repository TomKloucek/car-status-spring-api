package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Road {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "starting_point")
    private String startingPoint;
    @Basic
    @Column(name = "end_point")
    private String endPoint;
    @Basic
    @Column(name = "length")
    private Integer length;
    @OneToMany(mappedBy = "roadByRoadId")
    private Collection<Roadpath> roadpathsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Road road = (Road) o;

        if (id != road.id) return false;
        if (startingPoint != null ? !startingPoint.equals(road.startingPoint) : road.startingPoint != null)
            return false;
        if (endPoint != null ? !endPoint.equals(road.endPoint) : road.endPoint != null) return false;
        if (length != null ? !length.equals(road.length) : road.length != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startingPoint != null ? startingPoint.hashCode() : 0);
        result = 31 * result + (endPoint != null ? endPoint.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        return result;
    }

    public Collection<Roadpath> getRoadpathsById() {
        return roadpathsById;
    }

    public void setRoadpathsById(Collection<Roadpath> roadpathsById) {
        this.roadpathsById = roadpathsById;
    }
}

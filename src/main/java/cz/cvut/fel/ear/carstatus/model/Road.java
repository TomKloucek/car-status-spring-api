package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Road {
    private int id;
    private String startingPoint;
    private String endPoint;
    private Integer length;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "starting_point")
    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    @Basic
    @Column(name = "end_point")
    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Basic
    @Column(name = "length")
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
}

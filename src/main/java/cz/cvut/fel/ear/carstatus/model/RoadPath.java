package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class RoadPath {
    private int id;
    private Integer averageSpeed;
    private Integer roadtripId;
    private Integer roadId;
    private Roadtrip roadtripByRoadtripId;

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
    @Column(name = "average_speed")
    public Integer getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Integer averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Basic
    @Column(name = "roadtrip_id")
    public Integer getRoadtripId() {
        return roadtripId;
    }

    public void setRoadtripId(Integer roadtripId) {
        this.roadtripId = roadtripId;
    }

    @Basic
    @Column(name = "road_id")
    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadPath roadpath = (RoadPath) o;

        if (id != roadpath.id) return false;
        if (averageSpeed != null ? !averageSpeed.equals(roadpath.averageSpeed) : roadpath.averageSpeed != null)
            return false;
        if (roadtripId != null ? !roadtripId.equals(roadpath.roadtripId) : roadpath.roadtripId != null) return false;
        if (roadId != null ? !roadId.equals(roadpath.roadId) : roadpath.roadId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (averageSpeed != null ? averageSpeed.hashCode() : 0);
        result = 31 * result + (roadtripId != null ? roadtripId.hashCode() : 0);
        result = 31 * result + (roadId != null ? roadId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "roadtrip_id", referencedColumnName = "id")
    public Roadtrip getRoadtripByRoadtripId() {
        return roadtripByRoadtripId;
    }

    public void setRoadtripByRoadtripId(Roadtrip roadtripByRoadtripId) {
        this.roadtripByRoadtripId = roadtripByRoadtripId;
    }
}

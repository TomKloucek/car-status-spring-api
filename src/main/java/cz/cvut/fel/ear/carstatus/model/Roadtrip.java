package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Roadtrip {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "max_speed")
    private Integer maxSpeed;
    @Basic
    @Column(name = "with_malfunction")
    private Boolean withMalfunction;
    @Basic
    @Column(name = "length")
    private Integer length;
    @Basic
    @Column(name = "driver_id")
    private Integer driverId;
    @OneToMany(mappedBy = "roadtripByRoadtripId")
    private Collection<Roadpath> roadpathsById;
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driverByDriverId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Boolean getWithMalfunction() {
        return withMalfunction;
    }

    public void setWithMalfunction(Boolean withMalfunction) {
        this.withMalfunction = withMalfunction;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Roadtrip roadtrip = (Roadtrip) o;

        if (id != roadtrip.id) return false;
        if (maxSpeed != null ? !maxSpeed.equals(roadtrip.maxSpeed) : roadtrip.maxSpeed != null) return false;
        if (withMalfunction != null ? !withMalfunction.equals(roadtrip.withMalfunction) : roadtrip.withMalfunction != null)
            return false;
        if (length != null ? !length.equals(roadtrip.length) : roadtrip.length != null) return false;
        if (driverId != null ? !driverId.equals(roadtrip.driverId) : roadtrip.driverId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (maxSpeed != null ? maxSpeed.hashCode() : 0);
        result = 31 * result + (withMalfunction != null ? withMalfunction.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (driverId != null ? driverId.hashCode() : 0);
        return result;
    }

    public Collection<Roadpath> getRoadpathsById() {
        return roadpathsById;
    }

    public void setRoadpathsById(Collection<Roadpath> roadpathsById) {
        this.roadpathsById = roadpathsById;
    }

    public Driver getDriverByDriverId() {
        return driverByDriverId;
    }

    public void setDriverByDriverId(Driver driverByDriverId) {
        this.driverByDriverId = driverByDriverId;
    }
}

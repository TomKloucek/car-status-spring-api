package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Roadtrip {
    private int id;
    private Integer maxSpeed;
    private Integer averageSpeed;
    private Boolean withMalfunction;
    private Integer length;
    private Integer driverId;
    private Driver driverByDriverId;

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
    @Column(name = "max_speed")
    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
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
    @Column(name = "with_malfunction")
    public Boolean getWithMalfunction() {
        return withMalfunction;
    }

    public void setWithMalfunction(Boolean withMalfunction) {
        this.withMalfunction = withMalfunction;
    }

    @Basic
    @Column(name = "length")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Basic
    @Column(name = "driver_id")
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
        if (averageSpeed != null ? !averageSpeed.equals(roadtrip.averageSpeed) : roadtrip.averageSpeed != null)
            return false;
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
        result = 31 * result + (averageSpeed != null ? averageSpeed.hashCode() : 0);
        result = 31 * result + (withMalfunction != null ? withMalfunction.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (driverId != null ? driverId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    public Driver getDriverByDriverId() {
        return driverByDriverId;
    }

    public void setDriverByDriverId(Driver driverByDriverId) {
        this.driverByDriverId = driverByDriverId;
    }
}

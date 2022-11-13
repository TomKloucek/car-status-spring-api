package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Seat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "vertical_position")
    private Integer verticalPosition;
    @Basic
    @Column(name = "horizontal_position")
    private Integer horizontalPosition;
    @Basic
    @Column(name = "driver_seat")
    private Boolean driverSeat;
    @Basic
    @Column(name = "driver_id")
    private Integer driverId;
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driverByDriverId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(Integer verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public Integer getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(Integer horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public Boolean getDriverSeat() {
        return driverSeat;
    }

    public void setDriverSeat(Boolean driverSeat) {
        this.driverSeat = driverSeat;
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

        Seat seat = (Seat) o;

        if (id != seat.id) return false;
        if (verticalPosition != null ? !verticalPosition.equals(seat.verticalPosition) : seat.verticalPosition != null)
            return false;
        if (horizontalPosition != null ? !horizontalPosition.equals(seat.horizontalPosition) : seat.horizontalPosition != null)
            return false;
        if (driverSeat != null ? !driverSeat.equals(seat.driverSeat) : seat.driverSeat != null) return false;
        if (driverId != null ? !driverId.equals(seat.driverId) : seat.driverId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (verticalPosition != null ? verticalPosition.hashCode() : 0);
        result = 31 * result + (horizontalPosition != null ? horizontalPosition.hashCode() : 0);
        result = 31 * result + (driverSeat != null ? driverSeat.hashCode() : 0);
        result = 31 * result + (driverId != null ? driverId.hashCode() : 0);
        return result;
    }

    public Driver getDriverByDriverId() {
        return driverByDriverId;
    }

    public void setDriverByDriverId(Driver driverByDriverId) {
        this.driverByDriverId = driverByDriverId;
    }
}

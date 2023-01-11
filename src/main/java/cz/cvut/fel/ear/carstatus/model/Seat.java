package cz.cvut.fel.ear.carstatus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Seat extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int verticalPosition;

    @Basic(optional = false)
    @Column(nullable = false)
    private int horizontalPosition;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean driverSeat;


    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public boolean isDriverSeat() {
        return driverSeat;
    }

    public void setDriverSeat(boolean driverSeat) {
        this.driverSeat = driverSeat;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "verticalPosition=" + verticalPosition +
                ", horizontalPosition=" + horizontalPosition +
                ", driverSeat=" + driverSeat +
                '}';
    }
}

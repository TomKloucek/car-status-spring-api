package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
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
}

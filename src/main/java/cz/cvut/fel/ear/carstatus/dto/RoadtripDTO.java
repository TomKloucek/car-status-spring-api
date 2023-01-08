package cz.cvut.fel.ear.carstatus.dto;

import java.util.Date;

public class RoadtripDTO {
    private int maxSpeed;
    private boolean withMalfunction;
    private Date finished;

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public boolean isWithMalfunction() {
        return withMalfunction;
    }

    public Date getFinished() {
        return finished;
    }
}

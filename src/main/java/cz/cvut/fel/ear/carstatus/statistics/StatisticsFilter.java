package cz.cvut.fel.ear.carstatus.statistics;

import cz.cvut.fel.ear.carstatus.model.Driver;
import org.springframework.stereotype.Service;

import java.util.Date;

public class StatisticsFilter {
    private Date from;
    private Date to;
    private boolean malfunction;
    private Driver specificDriver;
    private String startDestination;
    private String finishDestination;

    public StatisticsFilter(Date from, Date to , Driver specificDriver, String startDestination, String finishDestination, boolean malfunction) {
        this.from = from;
        this.to = to;
        this.malfunction = malfunction;
        this.specificDriver = specificDriver;
        this.startDestination = startDestination;
        this.finishDestination = finishDestination;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Driver getSpecificDriver() {
        return specificDriver;
    }

    public void setSpecificDriver(Driver specificDriver) {
        this.specificDriver = specificDriver;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getFinishDestination() {
        return finishDestination;
    }

    public void setFinishDestination(String finishDestination) {
        this.finishDestination = finishDestination;
    }

    public boolean isMalfunction() {
        return malfunction;
    }

    public void setMalfunction(boolean malfunction) {
        this.malfunction = malfunction;
    }
}

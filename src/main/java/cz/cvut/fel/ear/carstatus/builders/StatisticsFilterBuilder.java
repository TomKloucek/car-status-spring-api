package cz.cvut.fel.ear.carstatus.builders;

import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.Date;

public class StatisticsFilterBuilder {

    private Date from;
    private Date to;
    private Driver specificDriver;
    private String startDestination;
    private String finishDestination;
    private boolean malfunction;

    public StatisticsFilterBuilder from(Date from) {
        this.from = from;
        return this;
    }
    public StatisticsFilterBuilder to(Date to) {
        this.to = to;
        return this;

    }
    public StatisticsFilterBuilder malfunction(Boolean malfunction) {
        if (malfunction == null) {
            malfunction = false;
        }
        this.malfunction = malfunction;
        return this;
    }
    public StatisticsFilterBuilder driver(Driver driver) {
        this.specificDriver = driver;
        return this;
    }
    public StatisticsFilterBuilder start(String start) {
        this.startDestination = start;
        return this;
    }
    public StatisticsFilterBuilder finish(String finish) {
        this.finishDestination = finish;
        return this;
    }

    public StatisticsFilter build() {
        return new StatisticsFilter(this.from,this.to,this.specificDriver,this.startDestination,this.finishDestination,this.malfunction);
    }
}

package cz.cvut.fel.ear.carstatus.statistics;

import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;

public class Statistics {
    private int numberOfRoadtrips;
    private int numberOfKm;
    private int averageSpeed;
    private EStatisticsType type;

    public Statistics(int numberOfRoadtrips, int numberOfKm, int averageSpeed, EStatisticsType type) {
        this.numberOfRoadtrips = numberOfRoadtrips;
        this.numberOfKm = numberOfKm;
        this.averageSpeed = averageSpeed;
        this.type = type;
    }

    public Statistics(int numberOfRoadtrips, int numberOfKm, int averageSpeed) {
        this.numberOfRoadtrips = numberOfRoadtrips;
        this.numberOfKm = numberOfKm;
        this.averageSpeed = averageSpeed;
    }

    public int getNumberOfRoadtrips() {
        return numberOfRoadtrips;
    }

    public void setNumberOfRoadtrips(int numberOfRoadtrips) {
        this.numberOfRoadtrips = numberOfRoadtrips;
    }

    public int getNumberOfKm() {
        return numberOfKm;
    }

    public void setNumberOfKm(int numberOfKm) {
        this.numberOfKm = numberOfKm;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public EStatisticsType getType() {
        return type;
    }

    public void setType(EStatisticsType type) {
        this.type = type;
    }
}

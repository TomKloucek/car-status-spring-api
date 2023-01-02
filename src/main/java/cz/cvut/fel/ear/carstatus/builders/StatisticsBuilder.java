package cz.cvut.fel.ear.carstatus.builders;

import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;
import cz.cvut.fel.ear.carstatus.interfaces.IStatistics;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.Statistics;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.Date;
import java.util.List;

public class StatisticsBuilder {
    private int numberOfRoadtrips;
    private int numberOfKm;
    private int averageSpeed;
    private EStatisticsType type;

    public StatisticsBuilder numberOfRoadtrips(List<Roadtrip> roadtripList) {
        this.numberOfRoadtrips = roadtripList.size();
        return this;
    }
    public StatisticsBuilder numberOfKm(List<Roadtrip> roadtripList) {
        int totalPath = 0;
        for (Roadtrip roadtrip : roadtripList) {
            for (Roadpath roadpath : roadtrip.getRoadpathList()) {
                Road road = roadpath.getRoad();
                totalPath += road.getLength();
            }
        }
        this.numberOfKm = totalPath;
        return this;

    }
    public StatisticsBuilder averageSpeed(List<Roadtrip> roadtripList) {
        int avgSpeed = 0;
        for (Roadtrip roadtrip : roadtripList) {
            for (Roadpath roadpath : roadtrip.getRoadpathList()) {
                Road road = roadpath.getRoad();
                avgSpeed += road.getLength()*roadpath.getAverageSpeed();
            }
        }
        this.averageSpeed = avgSpeed/roadtripList.size();
        return this;
    }

    public StatisticsBuilder type(EStatisticsType type) {
        this.type = type;
        return this;
    }

    public Statistics build() {
        return new Statistics(this.numberOfRoadtrips,this.numberOfKm,this.averageSpeed);
    }
}

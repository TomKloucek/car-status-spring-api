package cz.cvut.fel.ear.carstatus.builders;

import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.Statistics;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StatisticsBuilder {
    private int numberOfRoadtrips;
    private int numberOfKm;
    private int averageSpeed;
    private int maxSpeed;
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
        int totalDistance = 0;
        double totalTime = 0;
        for (Roadtrip roadtrip : roadtripList) {
            for (Roadpath roadpath : roadtrip.getRoadpathList()) {
                Road road = roadpath.getRoad();
                totalDistance += road.getLength();
                totalTime += road.getLength()/roadpath.getAverageSpeed();
            }
        }
        this.averageSpeed = (int) (totalDistance/totalTime);
        return this;
    }

    public StatisticsBuilder type(EStatisticsType type) {
        this.type = type;
        return this;
    }

    public StatisticsBuilder maxSpeed(List<Roadtrip> roadtripList) {
        Optional<Roadtrip> max =  roadtripList.stream().max(Comparator.comparingInt(Roadtrip::getMaxSpeed));
        max.ifPresent(roadtrip -> this.maxSpeed = roadtrip.getMaxSpeed());
        return this;
    }

    public Statistics build() {
        return new Statistics(this.numberOfRoadtrips,this.numberOfKm,this.averageSpeed,this.type,this.maxSpeed);
    }
}

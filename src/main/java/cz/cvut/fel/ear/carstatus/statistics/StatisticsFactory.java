package cz.cvut.fel.ear.carstatus.statistics;

import cz.cvut.fel.ear.carstatus.builders.StatisticsBuilder;
import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;
import cz.cvut.fel.ear.carstatus.filters.DestinationFilter;
import cz.cvut.fel.ear.carstatus.filters.DriverFilter;
import cz.cvut.fel.ear.carstatus.filters.NoFilter;
import cz.cvut.fel.ear.carstatus.filters.TimeFilter;
import cz.cvut.fel.ear.carstatus.interfaces.IFilter;
import cz.cvut.fel.ear.carstatus.interfaces.IStatistics;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatisticsFactory {
    RoadTripService roadTripService;
    @Autowired
    public StatisticsFactory(RoadTripService service) {
        this.roadTripService = service;
    }

    public Statistics getStatistics(StatisticsFilter filter) {

        if (filter.getFrom() == null && filter.getTo() == null) {
            return getCompleteStatistics(filter);
        }
        if (filter.getTo() != null && filter.getFrom() == null) {
            return getPreStatistics(filter);
        }
        else {
            if (filter.getFrom() == null) {
                filter.setFrom(new Date(Long.MIN_VALUE));
            }
            return getPeriodStatistics(filter);
        }
    }

    private StatisticsBuilder getStatisticsReady(StatisticsFilter filter) {
        IFilter handler = new NoFilter();
        IFilter driver = new DriverFilter();
        IFilter destination = new DestinationFilter();
        IFilter time = new TimeFilter();
        handler.setNext(driver);
        driver.setNext(destination);
        destination.setNext(time);
        List<Roadtrip> filtered = handler.handleRequest(filter,roadTripService.findAll());
        return new StatisticsBuilder().numberOfKm(filtered).numberOfRoadtrips(filtered).averageSpeed(filtered);
    }

    public Statistics getCompleteStatistics(StatisticsFilter filter) {
        return getStatisticsReady(filter).type(EStatisticsType.COMPLETE).build();
    }
    public Statistics getPreStatistics(StatisticsFilter filter) {
        return getStatisticsReady(filter).type(EStatisticsType.PRE).build();
    }

    public Statistics getPeriodStatistics(StatisticsFilter filter) {
        return getStatisticsReady(filter).type(EStatisticsType.PERIOD).build();
    }
}

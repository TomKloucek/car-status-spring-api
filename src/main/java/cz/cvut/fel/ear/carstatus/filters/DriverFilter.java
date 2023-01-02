package cz.cvut.fel.ear.carstatus.filters;

import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.ArrayList;
import java.util.List;

public class DriverFilter extends NoFilter {
    @Override
    public List<Roadtrip> handleRequest(StatisticsFilter filter, List<Roadtrip> roadtrips) {
        List<Roadtrip> result = new ArrayList<>();
        if (filter.getSpecificDriver() != null) {
            for (Roadtrip roadtrip : roadtrips) {
                if (roadtrip.getDriver().equals(filter.getSpecificDriver())) {
                    result.add(roadtrip);
                }
            }
            roadtrips = result;
        }
        if (filter.getFrom() != null || filter.getTo() != null || filter.getFinishDestination() != null || filter.getStartDestination() != null) {
            return  next.handleRequest(filter, roadtrips);
        }
        return roadtrips;
    }
}

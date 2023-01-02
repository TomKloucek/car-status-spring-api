package cz.cvut.fel.ear.carstatus.filters;

import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.ArrayList;
import java.util.List;

public class MalfunctionFilter extends NoFilter {
    @Override
    public List<Roadtrip> handleRequest(StatisticsFilter filter, List<Roadtrip> roadtrips) {
        List<Roadtrip> result = new ArrayList<>();
        for (Roadtrip roadtrip : roadtrips) {
            if (filter.isMalfunction()) {
                if (roadtrip.isWithMalfunction()) {
                    result.add(roadtrip);
                }
            } else {
                if (!roadtrip.isWithMalfunction()) {
                    result.add(roadtrip);
                }
            }
        }
        roadtrips = result;
        if (filter.getFrom() != null || filter.getTo() != null || filter.getSpecificDriver() != null || filter.getFinishDestination() != null || filter.getStartDestination() != null) {
            return this.next.handleRequest(filter, roadtrips);
        }
        return roadtrips;
    }
}

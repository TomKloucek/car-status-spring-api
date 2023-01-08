package cz.cvut.fel.ear.carstatus.filters;

import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.ArrayList;
import java.util.List;

public class DestinationFilter extends NoFilter {
    @Override
    public List<Roadtrip> handleRequest(StatisticsFilter filter, List<Roadtrip> roadtrips) {
        List<Roadtrip> result = new ArrayList<>();
        if (filter.getStartDestination() != null || filter.getFinishDestination() != null) {
            for (Roadtrip roadtrip : roadtrips) {
                for (Roadpath roadpath : roadtrip.getRoadpathList()) {
                    Road road = roadpath.getRoad();
                    if (road.getEndPoint().equals(filter.getFinishDestination()) || road.getStartingPoint().equals(filter.getStartDestination())) {
                        result.add(roadtrip);
                    }
            }
            roadtrips = result;
        }
    }
        if (this.next != null && (filter.getSpecificDriver() != null || filter.getFinishDestination() != null || filter.getStartDestination() != null)) {
            return this.next.handleRequest(filter, roadtrips);
        }
        return roadtrips;
    }
}

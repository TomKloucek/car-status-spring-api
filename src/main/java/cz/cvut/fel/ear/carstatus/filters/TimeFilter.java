package cz.cvut.fel.ear.carstatus.filters;

import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;
import cz.cvut.fel.ear.carstatus.util.Helpers;

import java.util.ArrayList;
import java.util.List;

public class TimeFilter extends NoFilter {
    @Override
    public List<Roadtrip> handleRequest(StatisticsFilter filter, List<Roadtrip> roadtrips) {
        List<Roadtrip> result = new ArrayList<>();
        if (filter.getFrom() != null || filter.getTo() != null) {
            for (Roadtrip roadtrip : roadtrips) {
                if (Helpers.isWithinRange(roadtrip.getFinished(), filter.getFrom(), filter.getTo())) {
                    result.add(roadtrip);
                }
            }
            roadtrips = result;
        }
        if (filter.getSpecificDriver() != null || filter.getFinishDestination() != null || filter.getStartDestination() != null) {
            return this.next.handleRequest(filter, roadtrips);
        }
        return roadtrips;
    }
}

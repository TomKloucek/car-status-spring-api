package cz.cvut.fel.ear.carstatus.filters;

import cz.cvut.fel.ear.carstatus.interfaces.IFilter;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.List;

public class NoFilter implements IFilter {

    protected IFilter next;

    @Override
    public void setNext(IFilter handler) {
        this.next = handler;
    }

    @Override
    public List<Roadtrip> handleRequest(StatisticsFilter filter, List<Roadtrip> roadtrips) {
        roadtrips = new MalfunctionFilter().handleRequest(filter, roadtrips);
        if (filter.getFrom() != null || filter.getTo() != null || filter.getSpecificDriver() != null || filter.getFinishDestination() != null || filter.getStartDestination() != null) {
            return this.next.handleRequest(filter, roadtrips);
        }
        return roadtrips;
    }
}

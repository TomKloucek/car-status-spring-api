package cz.cvut.fel.ear.carstatus.interfaces;

import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;

import java.util.List;

public interface IFilter {
    public void setNext(IFilter handler);

    public List<Roadtrip> handleRequest(StatisticsFilter filter, List<Roadtrip> roadtrips);
}

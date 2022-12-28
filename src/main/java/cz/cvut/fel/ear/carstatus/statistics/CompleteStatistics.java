package cz.cvut.fel.ear.carstatus.statistics;

import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;
import cz.cvut.fel.ear.carstatus.interfaces.IStatistics;

public class CompleteStatistics implements IStatistics {
    EStatisticsType type = EStatisticsType.COMPLETE;

    @Override
    public void createStatistics() {

    }
}

package cz.cvut.fel.ear.carstatus.statistics;

import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;
import cz.cvut.fel.ear.carstatus.interfaces.IStatistics;

public class PeriodStatistics implements IStatistics {
    EStatisticsType type = EStatisticsType.PERIOD;
    @Override
    public void createStatistics() {

    }
}

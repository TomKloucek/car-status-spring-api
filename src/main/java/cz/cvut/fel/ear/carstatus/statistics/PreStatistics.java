package cz.cvut.fel.ear.carstatus.statistics;

import cz.cvut.fel.ear.carstatus.enums.EStatisticsType;
import cz.cvut.fel.ear.carstatus.interfaces.IStatistics;

public class PreStatistics implements IStatistics {

    EStatisticsType type = EStatisticsType.PRE;
    @Override
    public void createStatistics() {

    }
}

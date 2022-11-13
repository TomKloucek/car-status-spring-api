package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Battery;

public class BatteryDao extends BaseDao<Battery> {

    protected BatteryDao(Class<Battery> type) {
        super(type);
    }
}

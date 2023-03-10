package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Battery;
import org.springframework.stereotype.Repository;

@Repository
public class BatteryDao extends BaseDao<Battery> {
    protected BatteryDao() {
        super(Battery.class);
    }
}

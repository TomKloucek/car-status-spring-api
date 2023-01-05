package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

@Service
public class BatteryService {
    private final BatteryDao dao;

    public BatteryService(BatteryDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Battery find(Integer id) {
        return dao.find(id);
    }

    public Battery getCurrentBattery() {
        Battery result = null;
         for (Battery b : dao.findAll()) {
             if (b.isInUsage()) {
                 result = b;
             }
         }
         return result;
    }

    @javax.transaction.Transactional
    public List<Battery> findAll() {
        return dao.findAll();
    }

    @Transactional
    public boolean changeCurrentBattery(Battery battery) {
        if (battery.getCapacity() < Constants.MINIMAL_BATTERY_CHARGE || battery.getCondition() < Constants.MINIMAL_BATTERY_CONDITION) {
            return false;
        }
        final Battery current = getCurrentBattery();
        current.setInUsage(false);
        updateBattery(current);
        battery.setInUsage(true);
        updateBattery(battery);
        return true;
    }

    public boolean batteryIsFunctional() {
        Battery battery = getCurrentBattery();
        return battery.getCapacity() >= Constants.MINIMAL_BATTERY_CHARGE && battery.getCondition() >= Constants.MINIMAL_BATTERY_CONDITION;
    }

    @Transactional
    public void deleteBattery(Battery battery) {
        dao.remove(battery);
    }

    @Transactional
    public void updateBattery(Battery battery) {
        dao.update(battery);
    }

    @Transactional
    public void createNewBattery(Battery battery) {
        dao.persist(battery);
    }
}

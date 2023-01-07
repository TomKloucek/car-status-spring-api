package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.BrokenPartException;
import cz.cvut.fel.ear.carstatus.log.Logger;
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
    private final Logger logger = new Logger();

    public BatteryService(BatteryDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Battery find(Integer id) {
        logger.log("Application found battery with ID: " + id + " in database.", ELoggerLevel.INFO);
        return dao.find(id);
    }

    public Battery getCurrentBattery() {
        Battery result = null;
         for (Battery b : dao.findAll()) {
             if (b.isInUsage()) {
                 result = b;
             }
         }
        logger.log("Application provided information about current battery.", ELoggerLevel.INFO);
        return result;
    }

    @Transactional
    public List<Battery> findAll() {
        logger.log("Application found all batteries in database.", ELoggerLevel.INFO);
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
        DataClass.getInstance().incrementNumberOfBatteriesChanged();
        logger.log("Current battery was changed.", ELoggerLevel.INFO);
        return true;
    }

    public void chargeBattery(){
        if(batteryIsFunctional()){
            getCurrentBattery().setCapacity(100);
            logger.log("Current battery was charged to 100%.", ELoggerLevel.INFO);
            DataClass.getInstance().incrementNumberOfChargingBatteries();
        }
        else{
            throw new BrokenPartException("Unable to charge battery which is broken (has lower capacity than minimal)");
        }
    }
    public boolean batteryIsFunctional() {
        Battery battery = getCurrentBattery();
        return battery.getCondition() >= Constants.MINIMAL_BATTERY_CONDITION;
    }

    @Transactional
    public void deleteBattery(Battery battery) {
        dao.remove(battery);
        logger.log("Battery with ID: "+battery.getId() +" was deleted.", ELoggerLevel.INFO);
    }

    @Transactional
    public void updateBattery(Battery battery) {
        dao.update(battery);
        logger.log("Battery with ID: "+battery.getId() +" was updated.", ELoggerLevel.INFO);
    }

    @Transactional
    public void createNewBattery(Battery battery) {
        dao.persist(battery);
        logger.log("New battery was created.", ELoggerLevel.INFO);
        DataClass.getInstance().incrementNumberOfBatteriesAdded();
    }
}

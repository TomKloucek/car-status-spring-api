package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.CarCheckDao;
import cz.cvut.fel.ear.carstatus.dao.MechanicDao;
import cz.cvut.fel.ear.carstatus.dao.UserDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.ValidationException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.rest.UserController;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarcheckService {
    private static final Logger logger = new Logger();
    private final CarCheckDao dao;
    private final UserDao userDao;
    private final MechanicDao mechanicDao;
    private final TyreService tyreService;
    private final BatteryService batteryService;
    private final LiquidService liquidService;

    public CarcheckService(CarCheckDao dao, UserDao userDao, MechanicDao mechanicDao, TyreService tyreService, BatteryService batteryService, LiquidService liquidService) {
        this.dao = dao;
        this.userDao = userDao;
        this.mechanicDao = mechanicDao;
        this.tyreService = tyreService;
        this.batteryService = batteryService;
        this.liquidService = liquidService;
    }

    @Transactional(readOnly = true)
    public Carcheck find(Integer id) {
        return dao.find(id);
    }

    public Carcheck getLastCarcheck() {
        return dao.getLastCarcheck();
    }

    public List<Carcheck> getCarchecksMadeByMechanic(Mechanic mechanic) {
        return dao.findAll().stream()
                .filter(c -> c.getMechanic().equals(mechanic))
                .collect(Collectors.toList());
    }
    @Transactional
    public void makeCarcheck(@CurrentSecurityContext(expression="authentication?.name")
                                 String username){
        Carcheck carcheck = new Carcheck();
        Mechanic mechanic;
        if(userDao.findByUsername(username).getRole() == Role.MECHANIC){
            mechanic = mechanicDao.findByUsername(username);
            carcheck.setMechanic(mechanic);
        }
        else{
            throw new ValidationException("Logged user is not a mechanic.");
        }
        Date date = new Date();
        if (date.after(mechanic.getOperatingTo())){
            throw new ValidationException("Mechanic does not operate during given date.");
        }
        else {
            carcheck.setCheckDate(date);
        }
        List<Tyre> tyres = tyreService.getCurrentTyres();
        Battery battery = batteryService.getCurrentBattery();
        List<Liquid> liquids = liquidService.findAll();
        tyres.stream()
                .filter(Tyre::isInUsage)
                .forEach(tyre -> {
                    if (tyre.getPressure() <= Constants.MINIMAL_TYRE_PRESSURE && tyre.getCondition() >= Constants.MINIMAL_TYRE_CONDITION) {
                        tyre.setPressure(33);
                    } else if (tyre.getCondition() < Constants.MINIMAL_TYRE_CONDITION) {
                        tyre.setInUsage(false);
                        Tyre newTyre = new Tyre();
                        newTyre.setInUsage(true);
                        newTyre.setCondition(100);
                        newTyre.setPosition(tyre.getPosition());
                        newTyre.setPressure(33);
                        tyreService.createNewTyre(newTyre);
                    }
                });
        if(battery.getCapacity() <= Constants.MINIMAL_BATTERY_CHARGE && battery.getCondition() >= Constants.MINIMAL_BATTERY_CONDITION){
            batteryService.chargeBattery();
        } else if (battery.getCapacity() <= Constants.MINIMAL_BATTERY_CHARGE && battery.getCondition() < Constants.MINIMAL_BATTERY_CONDITION) {
            Battery newBattery = new Battery();
            newBattery.setCapacity(100);
            newBattery.setCondition(100);
            newBattery.setInUsage(true);
            batteryService.changeCurrentBattery(newBattery);
        }
        liquids.stream()
                .map(Liquid::getType)
                .forEach(liquidService::refillLiquid);
        createNewCarcheck(carcheck);
        DataClass.getInstance().incrementNumberOfCarchecksMade();
        logger.log("Carcheck was done by mechanic "+mechanic.getUsername()+" successfully", ELoggerLevel.DEBUG);
    }


    @Transactional
    public List<Carcheck> findAll() {
        return dao.findAll();
    }


    @Transactional
    public void deleteCarcheck(Carcheck carcheck) {
        dao.remove(carcheck);
    }

    @Transactional
    public void updateCarcheck(Carcheck carcheck) {
        dao.update(carcheck);
    }

    @Transactional
    public void createNewCarcheck(Carcheck carcheck) {
        dao.persist(carcheck);
    }
}

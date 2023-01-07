package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.LiquidDao;
import cz.cvut.fel.ear.carstatus.dao.RoadDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Road;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class LiquidService {
    private final LiquidDao dao;
    private final Logger logger = new Logger();

    @Autowired
    public LiquidService(LiquidDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Liquid> findAll() {
        logger.log("Application found all liquids in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }

    @Transactional
    public Liquid find(Integer id) {
        logger.log("Application found liquid with ID: " + id + " in database.", ELoggerLevel.INFO);
        return dao.find(id);
    }

    @Transactional
    public void persist(Liquid liquid) {
        dao.persist(liquid);
        logger.log("New liquid was created.", ELoggerLevel.INFO);

    }

    @Transactional
    public void update(Liquid liquid) {
        dao.update(liquid);
        logger.log("Liquid with ID: "+liquid.getId() +" was updated.", ELoggerLevel.INFO);
    }

    @Transactional
    public void refillLiquid(String type) {
        final Liquid refill = dao.findByType(type);
        if(type == "cooling"){
            DataClass.getInstance().incrementNumberOfCoolingLiquidRefills();
        } else if (type == "braking") {
            DataClass.getInstance().incrementNumberOfBrakingLiquidReffils();
        }
        refill.setLevel(100);
        update(refill);
        logger.log("Liquid of type "+type+" was successfully refilled.", ELoggerLevel.DEBUG);
    }

    @Transactional
    public Liquid find(String type) {
        logger.log("Application liquid of type: " + type+ " in database.", ELoggerLevel.INFO);
        return dao.findByType(type);
    }

    @Transactional
    public void remove(Liquid liquid) {
        Objects.requireNonNull(liquid);
        dao.remove(liquid);
        logger.log("Liquid with ID: "+liquid.getId() +" was deleted.", ELoggerLevel.INFO);
    }
}

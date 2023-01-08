package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.RoadTripDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class RoadTripService {
    private final RoadTripDao dao;
    private final Logger logger = new Logger();


    @Autowired
    public RoadTripService(RoadTripDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Roadtrip> findAll() {
        logger.log("Application found all road trips in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }

    @Transactional
    public Roadtrip find(Integer id) {
        logger.log("Application tried to find road trip with ID: " + id + " in database.", ELoggerLevel.INFO);
        return dao.find(id);
    }

    @Transactional
    public void persist(Roadtrip roadtrip) {
        dao.persist(roadtrip);
        logger.log("New road trip was created.", ELoggerLevel.INFO);
    }

    @Transactional
    public void update(Roadtrip roadtrip) {
        dao.update(roadtrip);
        logger.log("Road trip with ID: "+roadtrip.getId() +" was updated.", ELoggerLevel.INFO);
    }

    @Transactional
    public void remove(Roadtrip roadtrip) {
        Objects.requireNonNull(roadtrip);
        dao.remove(roadtrip);
        logger.log("Road trip with ID: "+roadtrip.getId() +" was deleted.", ELoggerLevel.INFO);

    }

    @Transactional
    public Driver lastDriver() {
        logger.log("Application provided information bout last driver.", ELoggerLevel.INFO);
        return dao.findAll().get(dao.findAll().size()-1).getDriver();
    }
}

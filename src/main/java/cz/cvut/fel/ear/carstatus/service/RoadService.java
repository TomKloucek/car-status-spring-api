package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.RoadDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Road;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class RoadService {
    private final RoadDao dao;
    private final Logger logger = new Logger();


    @Autowired
    public RoadService(RoadDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Road> findAll() {
        logger.log("Application found all roads in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }

    @Transactional
    public Road find(Integer id) {
        logger.log("Application tried to find road with ID: " + id + " in database.", ELoggerLevel.INFO);
        return dao.find(id);
    }

    @Transactional
    public void persist(Road road) {
        dao.persist(road);
        logger.log("New road was created.", ELoggerLevel.INFO);
    }

    @Transactional
    public void update(Road road) {
        dao.update(road);
        logger.log("Road with ID: "+road.getId() +" was updated.", ELoggerLevel.INFO);
    }

    @Transactional
    public void remove(Road road) {
        Objects.requireNonNull(road);
        dao.remove(road);
        logger.log("Road with ID: "+road.getId() +" was deleted.", ELoggerLevel.INFO);
    }
}

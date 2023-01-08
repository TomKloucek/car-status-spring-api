package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.RoadPathDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class RoadPathService {
    private final RoadPathDao dao;
    private final Logger logger = new Logger();


    @Autowired
    public RoadPathService(RoadPathDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Roadpath> findAll() {
        logger.log("Application found all road paths in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }

    @Transactional
    public Roadpath find(Integer id) {
        logger.log("Application found road path with ID: " + id + " in database.", ELoggerLevel.INFO);
        return dao.find(id);
    }

    @Transactional
    public void persist(Roadpath roadpath) {
        dao.persist(roadpath);
        logger.log("New road path was created.", ELoggerLevel.INFO);
    }

    @Transactional
    public void update(Roadpath roadpath) {
        dao.update(roadpath);
        logger.log("Road path with ID: "+roadpath.getId() +" was updated.", ELoggerLevel.INFO);
    }

    @Transactional
    public void remove(Roadpath roadpath) {
        Objects.requireNonNull(roadpath);
        dao.remove(roadpath);
        logger.log("Road path with ID: "+roadpath.getId() +" was removed.", ELoggerLevel.INFO);
    }
}

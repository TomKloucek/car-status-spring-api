package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.MechanicDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MechanicService {
    private final MechanicDao dao;
    private final Logger logger = new Logger();


    public MechanicService(MechanicDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Mechanic find(Integer id) {
        logger.log("Application tried to find mechanic with ID: " + id + " in database.", ELoggerLevel.INFO);
        if(dao.find(id) == null){
            logger.log("Mechanic with ID: " + id + " was not found.", ELoggerLevel.INFO);
            throw NotFoundException.create("Mechanic", id);
        }
        else {
            logger.log("Mechanic with ID: " + id + " was found.", ELoggerLevel.ERROR);
            return dao.find(id);
        }
    }

    @Transactional
    public List<Mechanic> findAll() {
        logger.log("Application found all mechanics in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }
}

package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class DriverService {
    private final DriverDao dao;

    private final Logger logger;

    private final PasswordEncoder encoder;

    @Autowired
    public DriverService(DriverDao rd, Logger logger, PasswordEncoder encoder) {
        dao = rd;
        this.logger = logger;
        this.encoder = encoder;
    }

    @Transactional
    public List<Driver> findAll() {
        logger.log("Application found all drivers in database.", ELoggerLevel.INFO);
        return dao.findAll();
    }

    @Transactional
    public Driver find(Integer id) {
        logger.log("Application tried to find driver with ID: " + id + " in database.", ELoggerLevel.INFO);
        return dao.find(id);
    }

    @Transactional
    public void persist(Driver driver) {
        Objects.requireNonNull(driver);
        driver.setPassword(encoder.encode(driver.getPassword()));
        if (driver.getRole() == null) {
            driver.setRole(Constants.DEFAULT_ROLE);
        }
        DataClass.getInstance().incrementNumberOfDriversAdded();
        dao.persist(driver);
        logger.log("New driver was created.", ELoggerLevel.INFO);
    }

    @Transactional
    public void update(Driver driver) {
        dao.update(driver);
        logger.log("Driver with ID: "+driver.getId() +" was updated.", ELoggerLevel.INFO);
    }

    public List<Roadtrip> getAllRoadtripsMadeByDriver(Integer id) {
        logger.log("Application provided information about all road trips made by driver with ID: "+id+".", ELoggerLevel.INFO);
        return dao.find(id).getRoadtripList();
    }

    @Transactional
    public void remove(Driver driver) {
        Objects.requireNonNull(driver);
        dao.remove(driver);
        logger.log("Driver with ID: "+driver.getId() +" was removed.", ELoggerLevel.INFO);
    }
}

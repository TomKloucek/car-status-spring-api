package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.dao.RoadTripDao;
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

    private final PasswordEncoder encoder;

    @Autowired
    public DriverService(DriverDao rd, PasswordEncoder encoder) {
        dao = rd;
        this.encoder = encoder;
    }

    @Transactional
    public List<Driver> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Driver find(Integer id) {
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
    }

    @Transactional
    public void update(Driver driver) {
        dao.update(driver);
    }

    public List<Roadtrip> getAllRoadtripsMadeByDriver(Integer id) {
        return dao.find(id).getRoadtripList();
    }

    @Transactional
    public void remove(Driver driver) {
        Objects.requireNonNull(driver);
        dao.update(driver);
    }
}

package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.dao.RoadTripDao;
import cz.cvut.fel.ear.carstatus.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class DriverService {
    private final DriverDao dao;

    @Autowired
    public DriverService(DriverDao rd) {
        dao = rd;
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
        dao.persist(driver);
    }

    @Transactional
    public void update(Driver driver) {
        dao.update(driver);
    }

    @Transactional
    public void remove(Driver driver) {
        Objects.requireNonNull(driver);
        dao.update(driver);
    }
}

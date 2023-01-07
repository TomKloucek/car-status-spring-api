package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.dao.SeatDao;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Seat;
import cz.cvut.fel.ear.carstatus.util.Helpers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatService {

    private final SeatDao dao;
    private final DriverDao driverDao;

    public SeatService(SeatDao dao, DriverDao driverDao) {
        this.dao = dao;
        this.driverDao = driverDao;
    }

    @Transactional(readOnly = true)
    public Seat find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    @PreAuthorize("hasRole('DRIVER')")
    public Seat getCurrentDriversSeat() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            for (Seat s : dao.findAll()) {
                if (s.isDriverSeat() && driverDao.findByUsername(authentication.getName()).getSeatList().contains(s)) {
                    return s;
                }
            }
        return null;
    }

    @Transactional
    public boolean addVerticalPosition() {
        Seat driver = getCurrentDriversSeat();
        if (driver.getVerticalPosition() < 5) {
            driver.setVerticalPosition(driver.getVerticalPosition() + 1);
            dao.update(driver);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean subtractVerticalPosition() {
        Seat driver = getCurrentDriversSeat();
        if (driver.getVerticalPosition() > -5) {
            driver.setVerticalPosition(driver.getVerticalPosition() - 1);
            dao.update(driver);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addHorizontalPosition() {
        Seat driver = getCurrentDriversSeat();
        if (driver.getHorizontalPosition() < 5) {
            driver.setHorizontalPosition(driver.getHorizontalPosition() + 1);
            dao.update(driver);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean subtractHorizontalPosition() {
        Seat driver = getCurrentDriversSeat();
        if (driver.getHorizontalPosition() > -5) {
            driver.setHorizontalPosition(driver.getHorizontalPosition() - 1);
            dao.update(driver);
            return true;
        }
        return false;
    }

    @Transactional
    public List<Seat> findAll() {
        return dao.findAll();
    }

}

package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.dao.SeatDao;
import cz.cvut.fel.ear.carstatus.dao.TyreDao;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Seat;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.util.Constants;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatService {
    private final Logger logger = new Logger();
    private final SeatDao dao;

    public SeatService(SeatDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Seat find(Integer id) {
        return dao.find(id);
    }

    public Seat getCurrentDriversSeat() {
        for (Seat s : dao.findAll()) {
            if (s.isDriverSeat() && )
        }
        return result;
    }

    @Transactional
    public List<Seat> findAll() {
        return dao.findAll();
    }

    public void createNewSeat(Seat seat) {
        dao.persist(seat);
        logger.log("New seat was created.", ELoggerLevel.DEBUG);
    }

}

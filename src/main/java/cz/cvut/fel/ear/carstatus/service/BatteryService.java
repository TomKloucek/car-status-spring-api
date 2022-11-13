package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.model.Battery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BatteryService {
    private final BatteryDao dao;

    @Autowired
    public BatteryService(BatteryDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Battery find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Battery battery) {
        dao.persist(battery);
    }

    @Transactional
    public void update(Battery battery) {
        dao.update(battery);
    }

    @Transactional
    public void remove(Battery battery) {
        Objects.requireNonNull(battery);
        battery.setRemoved(true);
        dao.update(battery);
    }
}

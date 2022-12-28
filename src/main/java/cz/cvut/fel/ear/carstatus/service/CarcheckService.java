package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.dao.CarCheckDao;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarcheckService {
    private final CarCheckDao dao;

    public CarcheckService(CarCheckDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Carcheck find(Integer id) {
        return dao.find(id);
    }

    public Carcheck getLastCarcheck() {
        return dao.getLastCarcheck();
    }

    public List<Carcheck> getCarchecksMadeByMechanic(Mechanic mechanic) {
        List<Carcheck> result = new ArrayList<>();
        for (Carcheck c : dao.findAll()) {
            if (c.getMechanic().equals(mechanic)) {
                result.add(c);
            }
        }
        return result;
    }

    @javax.transaction.Transactional
    public List<Carcheck> findAll() {
        return dao.findAll();
    }
}

package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.CarCheckDao;
import cz.cvut.fel.ear.carstatus.dao.MechanicDao;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MechanicService {
    private final MechanicDao dao;

    public MechanicService(MechanicDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Mechanic find(Integer id) {
        return dao.find(id);
    }

    @javax.transaction.Transactional
    public List<Mechanic> findAll() {
        return dao.findAll();
    }
}

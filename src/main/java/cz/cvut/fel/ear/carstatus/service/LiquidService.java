package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.LiquidDao;
import cz.cvut.fel.ear.carstatus.dao.RoadDao;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Road;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class LiquidService {
    private final LiquidDao dao;

    @Autowired
    public LiquidService(LiquidDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Liquid> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Liquid find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Liquid liquid) {
        dao.persist(liquid);
    }

    @Transactional
    public void update(Liquid liquid) {
        dao.update(liquid);
    }

    @Transactional
    public void remove(Liquid liquid) {
        Objects.requireNonNull(liquid);
        dao.update(liquid);
    }
}
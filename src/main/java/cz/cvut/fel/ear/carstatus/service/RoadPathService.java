package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.dao.RoadPathDao;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class RoadPathService {
    private final RoadPathDao dao;

    @Autowired
    public RoadPathService(RoadPathDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Roadpath> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Roadpath find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Roadpath roadpath) {
        dao.persist(roadpath);
    }

    @Transactional
    public void update(Roadpath roadpath) {
        dao.update(roadpath);
    }

    @Transactional
    public void remove(Roadpath roadpath) {
        Objects.requireNonNull(roadpath);
        dao.update(roadpath);
    }
}

package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.RoadDao;
import cz.cvut.fel.ear.carstatus.model.Road;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class RoadService {
    private final RoadDao dao;

    @Autowired
    public RoadService(RoadDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Road> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Road find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Road road) {
        dao.persist(road);
    }

    @Transactional
    public void update(Road road) {
        dao.update(road);
    }

    @Transactional
    public void remove(Road road) {
        Objects.requireNonNull(road);
        dao.update(road);
    }
}

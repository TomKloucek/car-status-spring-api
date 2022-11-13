package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.RoadTripDao;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class RoadTripService {
    private final RoadTripDao dao;

    @Autowired
    public RoadTripService(RoadTripDao rd) {
        dao = rd;
    }

    @Transactional
    public List<Roadtrip> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Roadtrip find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Roadtrip roadtrip) {
        dao.persist(roadtrip);
    }

    @Transactional
    public void update(Roadtrip roadtrip) {
        dao.update(roadtrip);
    }

    @Transactional
    public void remove(Roadtrip roadtrip) {
        Objects.requireNonNull(roadtrip);
        dao.update(roadtrip);
    }
}

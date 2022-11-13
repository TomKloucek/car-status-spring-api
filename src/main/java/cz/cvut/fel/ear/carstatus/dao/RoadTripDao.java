package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import org.springframework.stereotype.Repository;

@Repository
public class RoadTripDao extends BaseDao<Roadtrip> {
    protected RoadTripDao() {
        super(Roadtrip.class);
    }
}

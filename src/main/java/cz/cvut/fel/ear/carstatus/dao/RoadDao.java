package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Road;
import org.springframework.stereotype.Repository;

@Repository
public class RoadDao extends BaseDao<Road> {
    protected RoadDao(Class<Road> type) {
        super(type);
    }
}

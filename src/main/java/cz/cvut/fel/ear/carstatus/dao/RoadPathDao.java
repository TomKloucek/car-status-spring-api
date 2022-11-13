package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Roadpath;
import org.springframework.stereotype.Repository;

@Repository
public class RoadPathDao extends BaseDao<Roadpath> {
    protected RoadPathDao() {
        super(Roadpath.class);
    }
}

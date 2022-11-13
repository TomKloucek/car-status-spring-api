package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Mechanic;
import org.springframework.stereotype.Repository;

@Repository
public class MechanicDao extends BaseDao<Mechanic> {
    protected MechanicDao() {
        super(Mechanic.class);
    }
}

package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Carcheck;
import org.springframework.stereotype.Repository;

@Repository
public class CarCheckDao extends BaseDao<Carcheck> {
    protected CarCheckDao() {
        super(Carcheck.class);
    }
}

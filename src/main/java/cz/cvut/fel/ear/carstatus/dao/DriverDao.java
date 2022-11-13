package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Driver;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDao extends BaseDao<Driver>{
    protected DriverDao() {
        super(Driver.class);
    }
}

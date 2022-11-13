package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Admin;
import org.springframework.stereotype.Repository;


public class AdminDao extends BaseDao<Admin>{
    protected AdminDao(Class<Admin> type) {
        super(type);
    }
}

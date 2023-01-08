package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import org.springframework.stereotype.Repository;

@Repository
public class CarCheckDao extends BaseDao<Carcheck> {
    protected CarCheckDao() {
        super(Carcheck.class);
    }

    public Carcheck getLastCarcheck() {
        Carcheck result = null;
        try {
            result = super.em.createNamedQuery("find_last_carcheck",Carcheck.class).getSingleResult();
        }catch (Exception e){
            throw new NotFoundException("Last car check was not found.");
        }
        return result;
    }
}

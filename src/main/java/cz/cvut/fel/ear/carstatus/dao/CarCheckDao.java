package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import org.springframework.stereotype.Repository;

import java.util.Objects;

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
            if(result != null){
                return result;
            }
            else {
                throw new NotFoundException("Last car check was not found.");
            }
        }
        return result;
    }
}

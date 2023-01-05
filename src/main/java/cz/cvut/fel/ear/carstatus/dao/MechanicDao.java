package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Mechanic;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class MechanicDao extends BaseDao<Mechanic> {
    protected MechanicDao() {
        super(Mechanic.class);
    }

    public Mechanic findByUsername(String username) {
        try {
            return em.createNamedQuery("Mechanic.findByUsername", Mechanic.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

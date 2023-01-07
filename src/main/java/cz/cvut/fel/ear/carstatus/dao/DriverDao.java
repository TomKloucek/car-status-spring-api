package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class DriverDao extends BaseDao<Driver>{
    protected DriverDao() {
        super(Driver.class);
    }

    public Driver findByUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", Driver.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

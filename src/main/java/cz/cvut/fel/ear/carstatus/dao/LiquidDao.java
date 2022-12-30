package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Liquid;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class LiquidDao extends BaseDao<Liquid> {
    protected LiquidDao() {
        super(Liquid.class);
    }

    public Liquid findByType(String type) {
        if (Objects.equals(type, "cooling")) {
            return super.em.createNamedQuery("find_cooling_liquid",Liquid.class).getSingleResult();
        } else {
            return super.em.createNamedQuery("find_braking_liquid",Liquid.class).getSingleResult();
        }
    }
}

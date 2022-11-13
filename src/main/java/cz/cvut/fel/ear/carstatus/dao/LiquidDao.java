package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Liquid;
import org.springframework.stereotype.Repository;

@Repository
public class LiquidDao extends BaseDao<Liquid> {
    protected LiquidDao() {
        super(Liquid.class);
    }
}

package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Tyre;
import org.springframework.stereotype.Repository;

@Repository
public class TyreDao extends BaseDao<Tyre> {
    protected TyreDao(Class<Tyre> type) {
        super(type);
    }
}

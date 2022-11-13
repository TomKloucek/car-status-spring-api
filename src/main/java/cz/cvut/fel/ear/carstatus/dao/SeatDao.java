package cz.cvut.fel.ear.carstatus.dao;

import cz.cvut.fel.ear.carstatus.model.Seat;
import org.springframework.stereotype.Repository;

@Repository
public class SeatDao extends BaseDao<Seat> {
    protected SeatDao() {
        super(Seat.class);
    }
}

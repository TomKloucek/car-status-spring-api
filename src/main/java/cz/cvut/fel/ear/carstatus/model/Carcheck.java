package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Carcheck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private Date checkDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Mechanic mechanic;


    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }
}

package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@NamedQuery(name = "find_last_carcheck", query = "SELECT c FROM Carcheck c ORDER BY c.checkDate desc")
@Entity
public class Carcheck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private Date checkDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    @JsonIgnore
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

    @Override
    public String toString() {
        return "Carcheck{" +
                "checkDate=" + checkDate +
                ", mechanic=" + mechanic +
                '}';
    }
}

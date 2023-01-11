package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NamedQuery(name = "find_last_carcheck", query = "SELECT c FROM Carcheck c ORDER BY c.checkDate desc")
@Getter
@Setter
@Entity
public class Carcheck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private Date checkDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Mechanic mechanic;


    @Override
    public String toString() {
        return "Carcheck{" +
                "checkDate=" + checkDate +
                ", mechanic=" + mechanic +
                '}';
    }
}

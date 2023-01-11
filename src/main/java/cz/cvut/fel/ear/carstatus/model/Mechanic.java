package cz.cvut.fel.ear.carstatus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "Mechanic.findByUsername", query = "SELECT DISTINCT mechanic FROM Mechanic mechanic WHERE mechanic.username = :username")
@Setter
@Getter
@DiscriminatorValue("MECHANIC")
public class Mechanic extends User {
    @Basic(optional = false)
    @Column(nullable = false)
    private Date operatingFrom;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date operatingTo;

    @Basic(optional = false)
    @Column(nullable = false)
    private String phoneNumber;


    @Override
    public String toString() {
        return "Mechanic{" +
                "operatingFrom=" + operatingFrom +
                ", operatingTo=" + operatingTo +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}

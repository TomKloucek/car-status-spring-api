package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "Mechanic.findByUsername", query = "SELECT DISTINCT mechanic FROM Mechanic mechanic WHERE mechanic.username = :username")
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

    public Date getOperatingFrom() {
        return operatingFrom;
    }

    public void setOperatingFrom(Date operatingFrom) {
        this.operatingFrom = operatingFrom;
    }

    public Date getOperatingTo() {
        return operatingTo;
    }

    public void setOperatingTo(Date operatingTo) {
        this.operatingTo = operatingTo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "Mechanic{" +
                "operatingFrom=" + operatingFrom +
                ", operatingTo=" + operatingTo +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}

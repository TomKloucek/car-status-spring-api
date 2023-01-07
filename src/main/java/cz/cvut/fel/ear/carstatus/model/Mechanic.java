package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Mechanic.findByUsername", query = "SELECT DISTINCT mechanic FROM Mechanic mechanic WHERE mechanic.username = :username")
})
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


//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private User user;

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

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

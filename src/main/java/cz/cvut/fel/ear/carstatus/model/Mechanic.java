package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
public class Mechanic extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private Date operatingFrom;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date operatingTo;

    @Basic(optional = false)
    @Column(nullable = false)
    private int phoneNumber;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "carcheck_id")
    private List<Carcheck> carcheckList;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Carcheck> getCarcheckList() {
        return carcheckList;
    }

    public void setCarcheckList(List<Carcheck> carcheckList) {
        this.carcheckList = carcheckList;
    }
}

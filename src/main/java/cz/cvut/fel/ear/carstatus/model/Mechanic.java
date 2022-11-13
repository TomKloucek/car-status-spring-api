package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Mechanic {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "operating_from")
    private Date operatingFrom;
    @Basic
    @Column(name = "operating_to")
    private Date operatingTo;
    @Basic
    @Column(name = "phone_number")
    private Integer phoneNumber;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @OneToMany(mappedBy = "mechanicByMechanicId")
    private Collection<Carcheck> carchecksById;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userByUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mechanic mechanic = (Mechanic) o;

        if (id != mechanic.id) return false;
        if (operatingFrom != null ? !operatingFrom.equals(mechanic.operatingFrom) : mechanic.operatingFrom != null)
            return false;
        if (operatingTo != null ? !operatingTo.equals(mechanic.operatingTo) : mechanic.operatingTo != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(mechanic.phoneNumber) : mechanic.phoneNumber != null)
            return false;
        if (userId != null ? !userId.equals(mechanic.userId) : mechanic.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operatingFrom != null ? operatingFrom.hashCode() : 0);
        result = 31 * result + (operatingTo != null ? operatingTo.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    public Collection<Carcheck> getCarchecksById() {
        return carchecksById;
    }

    public void setCarchecksById(Collection<Carcheck> carchecksById) {
        this.carchecksById = carchecksById;
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}

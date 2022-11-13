package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Carcheck {
    private int id;
    private Date checkDate;
    private Integer mechanicId;
    private Mechanic mechanicByMechanicId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "check_date")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Basic
    @Column(name = "mechanic_id")
    public Integer getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Integer mechanicId) {
        this.mechanicId = mechanicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carcheck carcheck = (Carcheck) o;

        if (id != carcheck.id) return false;
        if (checkDate != null ? !checkDate.equals(carcheck.checkDate) : carcheck.checkDate != null) return false;
        if (mechanicId != null ? !mechanicId.equals(carcheck.mechanicId) : carcheck.mechanicId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (checkDate != null ? checkDate.hashCode() : 0);
        result = 31 * result + (mechanicId != null ? mechanicId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "mechanic_id", referencedColumnName = "id")
    public Mechanic getMechanicByMechanicId() {
        return mechanicByMechanicId;
    }

    public void setMechanicByMechanicId(Mechanic mechanicByMechanicId) {
        this.mechanicByMechanicId = mechanicByMechanicId;
    }
}

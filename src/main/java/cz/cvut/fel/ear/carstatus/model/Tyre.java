package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Tyre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name = "tyre_pressure")
    private BigInteger tyrePressure;
    @Basic
    @Column(name = "condition")
    private Integer condition;
    @Basic
    @Column(name = "in_usage")
    private Boolean inUsage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigInteger getTyrePressure() {
        return tyrePressure;
    }

    public void setTyrePressure(BigInteger tyrePressure) {
        this.tyrePressure = tyrePressure;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Boolean getInUsage() {
        return inUsage;
    }

    public void setInUsage(Boolean inUsage) {
        this.inUsage = inUsage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tyre tyre = (Tyre) o;

        if (id != tyre.id) return false;
        if (position != null ? !position.equals(tyre.position) : tyre.position != null) return false;
        if (tyrePressure != null ? !tyrePressure.equals(tyre.tyrePressure) : tyre.tyrePressure != null) return false;
        if (condition != null ? !condition.equals(tyre.condition) : tyre.condition != null) return false;
        if (inUsage != null ? !inUsage.equals(tyre.inUsage) : tyre.inUsage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (tyrePressure != null ? tyrePressure.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (inUsage != null ? inUsage.hashCode() : 0);
        return result;
    }
}

package cz.cvut.fel.ear.carstatus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Battery extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int capacity;
    @Basic(optional = false)
    @Column(nullable = false)
    private int condition;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean inUsage;


    @Override
    public String toString() {
        return "Battery{" +
                "capacity=" + capacity +
                ", condition=" + condition +
                ", inUsage=" + inUsage +
                '}';
    }
}

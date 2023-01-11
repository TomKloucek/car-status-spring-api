package cz.cvut.fel.ear.carstatus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Tyre extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int position;

    @Basic(optional = false)
    @Column(nullable = false)
    private double pressure;


    @Basic(optional = false)
    @Column(nullable = false)
    private int condition;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean inUsage;


    @Override
    public String toString() {
        return "Tyre{" +
                "position='" + position + '\'' +
                ", pressure=" + pressure +
                ", condition=" + condition +
                ", inUsage=" + inUsage +
                '}';
    }
}

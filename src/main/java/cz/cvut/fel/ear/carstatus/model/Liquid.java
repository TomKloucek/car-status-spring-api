package cz.cvut.fel.ear.carstatus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@NamedQuery(name = "find_cooling_liquid", query = "SELECT l FROM Liquid l WHERE l.type='cooling'")
@NamedQuery(name = "find_braking_liquid", query = "SELECT l FROM Liquid l WHERE l.type='braking'")
@Getter
@Setter
@Entity
public class Liquid extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private int level;

    @Basic(optional = false)
    @Column(nullable = false)
    private int minLevel;

    @Basic(optional = false)
    @Column(nullable = false)
    private String type;

    public boolean checkWhetherIsBelowOrAtMinLevel(){
        return minLevel >= level;
    }

    @Override
    public String toString() {
        return "Liquid{" +
                "level=" + level +
                ", minLevel=" + minLevel +
                ", type='" + type + '\'' +
                '}';
    }
}

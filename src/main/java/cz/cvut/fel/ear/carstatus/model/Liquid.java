package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

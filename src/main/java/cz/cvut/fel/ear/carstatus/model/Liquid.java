package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Liquid {
    private int id;
    private Integer level;
    private Integer minimalLevel;
    private String type;

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
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "minimal_level")
    public Integer getMinimalLevel() {
        return minimalLevel;
    }

    public void setMinimalLevel(Integer minimalLevel) {
        this.minimalLevel = minimalLevel;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Liquid liquid = (Liquid) o;

        if (id != liquid.id) return false;
        if (level != null ? !level.equals(liquid.level) : liquid.level != null) return false;
        if (minimalLevel != null ? !minimalLevel.equals(liquid.minimalLevel) : liquid.minimalLevel != null)
            return false;
        if (type != null ? !type.equals(liquid.type) : liquid.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (minimalLevel != null ? minimalLevel.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

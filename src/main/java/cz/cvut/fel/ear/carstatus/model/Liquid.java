package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Liquid {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "level")
    private Integer level;
    @Basic
    @Column(name = "minimal_level")
    private Integer minimalLevel;
    @Basic
    @Column(name = "type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMinimalLevel() {
        return minimalLevel;
    }

    public void setMinimalLevel(Integer minimalLevel) {
        this.minimalLevel = minimalLevel;
    }

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

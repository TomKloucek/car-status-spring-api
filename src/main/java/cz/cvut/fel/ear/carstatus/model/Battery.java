package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Battery {
    private int id;
    private Integer capacity;
    private Integer condition;
    private Boolean inUsage;

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
    @Column(name = "capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "condition")
    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    @Basic
    @Column(name = "in_usage")
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

        Battery battery = (Battery) o;

        if (id != battery.id) return false;
        if (capacity != null ? !capacity.equals(battery.capacity) : battery.capacity != null) return false;
        if (condition != null ? !condition.equals(battery.condition) : battery.condition != null) return false;
        if (inUsage != null ? !inUsage.equals(battery.inUsage) : battery.inUsage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (inUsage != null ? inUsage.hashCode() : 0);
        return result;
    }
}

package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;

@Entity
public class Battery {
    @Id
    @GeneratedValue
    private int id;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer condition;
    @Basic(optional = false)
    @Column(nullable = false)
    private Boolean inUsage;

    private boolean removed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

}

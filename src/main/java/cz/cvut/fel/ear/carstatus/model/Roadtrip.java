package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Roadtrip extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int maxSpeed;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean withMalfunction;

    @Basic(optional = false)
    @Column(nullable = false)
    private Date finished;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "roadtrip_id")
    @OrderBy("road")
    private List<Roadpath> roadpathList;

    @ManyToOne
    @JsonBackReference
    private Driver driver;

    @Override
    public String toString() {
        return "Roadtrip{" +
                "maxSpeed=" + maxSpeed +
                ", withMalfunction=" + withMalfunction +
                ", finished=" + finished +
                ", driver=" + driver +
                '}';
    }
}

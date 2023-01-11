package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class Roadpath extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private int averageSpeed;

    @JsonBackReference
    @OneToOne
    private Roadtrip roadtrip;

    @JsonBackReference
    @OneToOne
    private Road road;

    @Override
    public String toString() {
        return "Roadpath{" +
                "averageSpeed=" + averageSpeed +
                ", road=" + road +
                '}';
    }
}

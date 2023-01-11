package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Road extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String startingPoint;

    @Basic(optional = false)
    @Column(nullable = false)
    private String endPoint;

    @Basic(optional = false)
    @Column(nullable = false)
    private int length;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "road_id")
    private List<Roadpath> roadpathList;


    @Override
    public String toString() {
        return "Road{" +
                "startingPoint='" + startingPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", length=" + length +
                ", roadpathList=" + roadpathList +
                '}';
    }
}

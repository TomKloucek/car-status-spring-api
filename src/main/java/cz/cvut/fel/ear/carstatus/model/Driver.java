package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("DRIVER")
public class Driver extends User{

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "driver_id")
    private List<Seat> seatList;


    @OneToMany
    @JoinColumn(name = "driver_id")
    @OrderBy("maxSpeed")
    @JsonManagedReference
    private List<Roadtrip> roadtripList;


    @Override
    public String toString() {
        return "Driver{" +
                "seatList=" + seatList +
                ", roadtripList=" + roadtripList +
//                ", user=" + user +
                '}';
    }
}

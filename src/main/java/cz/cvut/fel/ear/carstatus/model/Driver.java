package cz.cvut.fel.ear.carstatus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User{

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "driver_id")
    private List<Seat> seatList;


    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "driver_id")
    private List<Roadtrip> roadtripList;

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public List<Roadtrip> getRoadtripList() {
        return roadtripList;
    }

    public void setRoadtripList(List<Roadtrip> roadtripList) {
        this.roadtripList = roadtripList;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "seatList=" + seatList +
                ", roadtripList=" + roadtripList +
//                ", user=" + user +
                '}';
    }
}

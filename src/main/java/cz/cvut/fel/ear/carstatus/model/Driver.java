package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Driver extends User{

    @OneToMany
    @JoinColumn(name = "seat_id")
    private List<Seat> seatList;


    @OneToMany
    @JoinColumn(name = "roadtrip_id")
    private List<Roadtrip> roadtripList;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
}

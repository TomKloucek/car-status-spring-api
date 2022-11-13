package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Driver {
    private int id;
    private Integer userId;
    private User userByUserId;
    private Collection<Roadtrip> roadtripsById;
    private Collection<Seat> seatsById;

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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (id != driver.id) return false;
        if (userId != null ? !userId.equals(driver.userId) : driver.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @OneToMany(mappedBy = "driverByDriverId")
    public Collection<Roadtrip> getRoadtripsById() {
        return roadtripsById;
    }

    public void setRoadtripsById(Collection<Roadtrip> roadtripsById) {
        this.roadtripsById = roadtripsById;
    }

    @OneToMany(mappedBy = "driverByDriverId")
    public Collection<Seat> getSeatsById() {
        return seatsById;
    }

    public void setSeatsById(Collection<Seat> seatsById) {
        this.seatsById = seatsById;
    }
}

package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Driver {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userByUserId;
    @OneToMany(mappedBy = "driverByDriverId")
    private Collection<Roadtrip> roadtripsById;
    @OneToMany(mappedBy = "driverByDriverId")
    private Collection<Seat> seatsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Collection<Roadtrip> getRoadtripsById() {
        return roadtripsById;
    }

    public void setRoadtripsById(Collection<Roadtrip> roadtripsById) {
        this.roadtripsById = roadtripsById;
    }

    public Collection<Seat> getSeatsById() {
        return seatsById;
    }

    public void setSeatsById(Collection<Seat> seatsById) {
        this.seatsById = seatsById;
    }
}

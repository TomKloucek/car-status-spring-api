package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class User {
    private int id;
    private String firstname;
    private String surname;
    private String username;
    private Date birthdate;
    private String passw;
    private Collection<Admin> adminsById;
    private Collection<Driver> driversById;
    private Collection<Mechanic> mechanicsById;

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
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "birthdate")
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "passw")
    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (birthdate != null ? !birthdate.equals(user.birthdate) : user.birthdate != null) return false;
        if (passw != null ? !passw.equals(user.passw) : user.passw != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (passw != null ? passw.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Admin> getAdminsById() {
        return adminsById;
    }

    public void setAdminsById(Collection<Admin> adminsById) {
        this.adminsById = adminsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Driver> getDriversById() {
        return driversById;
    }

    public void setDriversById(Collection<Driver> driversById) {
        this.driversById = driversById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Mechanic> getMechanicsById() {
        return mechanicsById;
    }

    public void setMechanicsById(Collection<Mechanic> mechanicsById) {
        this.mechanicsById = mechanicsById;
    }
}

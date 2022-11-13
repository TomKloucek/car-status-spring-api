package cz.cvut.fel.ear.carstatus.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
// We can't name the table User, as it is a reserved table name in some dbs, including Postgres
@Table(name = "EAR_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    @Basic
    @Column(nullable = false, unique = true, name = "username", table = "User")
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", table = "User")
    private int id;
    @Basic
    @Column(name = "firstname", table = "User")
    private String firstname;
    @Basic
    @Column(name = "surname", table = "User")
    private String surname;
    @Basic
    @Column(name = "birthdate", table = "User")
    private Date birthdate;
    @Basic
    @Column(name = "passw", table = "User")
    private String passw;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Admin> adminsById;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Driver> driversById;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Mechanic> mechanicsById;

    public User() {
        this.role = Role.DRIVER;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void erasePassword() {
        this.password = null;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    @Override
    public String toString() {
        return "User{" +
                firstName + " " + lastName +
                "(" + username + ")}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

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
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
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

    public Collection<Admin> getAdminsById() {
        return adminsById;
    }

    public void setAdminsById(Collection<Admin> adminsById) {
        this.adminsById = adminsById;
    }

    public Collection<Driver> getDriversById() {
        return driversById;
    }

    public void setDriversById(Collection<Driver> driversById) {
        this.driversById = driversById;
    }

    public Collection<Mechanic> getMechanicsById() {
        return mechanicsById;
    }

    public void setMechanicsById(Collection<Mechanic> mechanicsById) {
        this.mechanicsById = mechanicsById;
    }
}

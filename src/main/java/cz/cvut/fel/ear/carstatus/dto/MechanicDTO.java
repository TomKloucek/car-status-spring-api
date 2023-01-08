package cz.cvut.fel.ear.carstatus.dto;

import cz.cvut.fel.ear.carstatus.model.Role;

import java.util.Date;

public class MechanicDTO {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String username;
    private String password;
    private Date operatingFrom;
    private Date operatingTo;
    private String phoneNumber;

    private Role role;

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getOperatingFrom() {
        return operatingFrom;
    }

    public Date getOperatingTo() {
        return operatingTo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

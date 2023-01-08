package cz.cvut.fel.ear.carstatus.dto;

import cz.cvut.fel.ear.carstatus.model.Role;

import java.util.Date;

public class UserDTO {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String username;
    private String password;
    private Role role;

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

    public Role getRole() {
        return role;
    }
}

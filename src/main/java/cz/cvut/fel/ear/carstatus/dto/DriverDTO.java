package cz.cvut.fel.ear.carstatus.dto;
import java.util.Date;

public class DriverDTO {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String username;
    private String password;

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
}

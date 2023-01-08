package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.EarException;
import cz.cvut.fel.ear.carstatus.exception.ValidationException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.service.UserService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("/rest/user")
public class UserController {
    private final Logger loggerToFile = new Logger();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addUser(@RequestBody(required = false) User user) {
        if(user.getRole() == Role.DRIVER){
            Driver driver = new Driver();
            driver.setFirstName(user.getFirstName());
            driver.setLastName(user.getLastName());
            Date date = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
            if(user.getBirthDate().before(date) || user.getBirthDate().after(new Date())){
                loggerToFile.log("Tried to register user with pointless date of birth.", ELoggerLevel.ERROR);
                throw new EarException("Tried to register user with pointless date of birth.");
            }
            driver.setBirthDate(user.getBirthDate());
            driver.setUsername(user.getUsername());
            driver.setPassword(user.getPassword());
            userService.persist(driver);
            loggerToFile.log("Driver "+ user.getUsername() +" successfully registered.", ELoggerLevel.INFO);
        }

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register-admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addAmin(@RequestBody Admin admin) {
        if(admin.getRole() == Role.ADMIN){
            Date date = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
            if(admin.getBirthDate().before(date) || admin.getBirthDate().after(new Date()) || admin.getExpires().before(date)){
                loggerToFile.log("Tried to register admin with pointless date of birth.", ELoggerLevel.ERROR);
                throw new EarException("Tried to register admin with pointless date of birth.");
            }
            userService.persist(admin);
            loggerToFile.log("Admin "+ admin.getUsername() +" successfully registered.", ELoggerLevel.INFO);
        }
        else{
            loggerToFile.log("EXCEPTION: Tried to create admin, but role of added user was not admin.",ELoggerLevel.ERROR);
            throw new ValidationException("EXCEPTION: Tried to create admin, but role of added user was not admin.");
        }

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", admin.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register-mechanic", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addMechanic(@RequestBody Mechanic mechanic) {
        if(mechanic.getRole() == Role.MECHANIC){
            Date date = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
            if(mechanic.getBirthDate().before(date) || mechanic.getBirthDate().after(new Date())){
                loggerToFile.log("Tried to register mechanic with pointless date of birth.", ELoggerLevel.ERROR);
                throw new EarException("Tried to register mechanic with pointless date of birth.");
            }
            if(mechanic.getOperatingFrom().before(date) || mechanic.getOperatingTo().before(new Date())){
                loggerToFile.log("Tried to register mechanic with pointless dates of operation.", ELoggerLevel.ERROR);
                throw new EarException("Tried to register mechanic with pointless date of operation.");
            }
            userService.persist(mechanic);
            loggerToFile.log("Mechanic "+ mechanic.getUsername() +" successfully registered.", ELoggerLevel.INFO);
        }
        else{
            loggerToFile.log("EXCEPTION: Tried to create mechanic, but role of added user was not mechanic.", ELoggerLevel.ERROR);
            throw new ValidationException("EXCEPTION: Tried to create mechanic, but role of added user was not mechanic.");
        }
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", mechanic.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public String getCurrent(@CurrentSecurityContext(expression="authentication?.name")
                        String username) {
        return "Current logged user: " + username;
    }
}

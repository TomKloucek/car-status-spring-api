package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.ValidationException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.service.UserService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/user")
public class UserController {
    private final Logger loggerToFile = new Logger();
    private final UserService userService;

    private static final String SUCCESSFULLY_REGISTERED = " successfully registered.";
    private static final String ID = "/{id}";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addUser(@RequestBody(required = false) User user) {
        if(user.getRole() == Role.DRIVER){
            Driver driver = new Driver();
            driver.setFirstName(user.getFirstName());
            driver.setLastName(user.getLastName());
            driver.setBirthDate(user.getBirthDate());
            driver.setUsername(user.getUsername());
            driver.setPassword(user.getPassword());
            userService.persist(driver);
            loggerToFile.log("Driver "+ user.getUsername() + SUCCESSFULLY_REGISTERED, ELoggerLevel.DEBUG);
        }

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri(ID, user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register-admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addAmin(@RequestBody Admin admin) {
        if(admin.getRole() == Role.ADMIN){
            userService.persist(admin);
            loggerToFile.log("Admin "+ admin.getUsername() + SUCCESSFULLY_REGISTERED, ELoggerLevel.DEBUG);
        }
        else{
            loggerToFile.log("EXCEPTION: Tried to create admin, but role of added user was not admin.",ELoggerLevel.ERROR);
            throw new ValidationException("EXCEPTION: Tried to create admin, but role of added user was not admin.");
        }

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri(ID, admin.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register-mechanic", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addMechanic(@RequestBody Mechanic mechanic) {
        if(mechanic.getRole() == Role.MECHANIC){
            userService.persist(mechanic);
            loggerToFile.log("Mechanic "+ mechanic.getUsername() + SUCCESSFULLY_REGISTERED, ELoggerLevel.DEBUG);
        }
        else{
            loggerToFile.log("EXCEPTION: Tried to create mechanic, but role of added user was not mechanic.", ELoggerLevel.ERROR);
            throw new ValidationException("EXCEPTION: Tried to create mechanic, but role of added user was not mechanic.");
        }
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri(ID, mechanic.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public String getCurrent(@CurrentSecurityContext(expression="authentication?.name")
                        String username) {
        return "Current logged user: " + username;
    }
}

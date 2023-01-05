package cz.cvut.fel.ear.carstatus.rest;

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

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final Logger loggerToFile = new Logger();
    private final UserService userService;

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
            loggerToFile.log("Driver "+ user.getUsername() +" successfully registered.");
            LOG.debug("Driver {} successfully registered.", driver.getUsername());
        }

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/register-admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addAmin(@RequestBody Admin admin) {
        if(admin.getRole() == Role.ADMIN){
            userService.persist(admin);
            loggerToFile.log("Admin "+ admin.getUsername() +" successfully registered.");
            LOG.debug("Admin {} successfully registered.", admin.getUsername());
        }
        else{
            loggerToFile.log("EXCEPTION: Tried to create admin, but role of added user was not admin.");
            LOG.error("EXCEPTION: Tried to create admin, but role of added user was not admin.");
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
            userService.persist(mechanic);
            loggerToFile.log("Mechanic "+ mechanic.getUsername() +" successfully registered.");
            LOG.debug("Mechanic {} successfully registered.", mechanic.getUsername());
        }
        else{
            loggerToFile.log("EXCEPTION: Tried to create mechanic, but role of added user was not mechanic.");
            LOG.error("EXCEPTION: Tried to create mechanic, but role of added user was not mechanic.");
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

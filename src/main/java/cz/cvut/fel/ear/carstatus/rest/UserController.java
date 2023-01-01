package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Role;
import cz.cvut.fel.ear.carstatus.model.User;
import cz.cvut.fel.ear.carstatus.service.UserService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/user")
public class UserController {
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
        }
        //TODO make admin and mechanic creation
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}

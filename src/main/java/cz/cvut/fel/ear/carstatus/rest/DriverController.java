package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.exception.ValidationException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.service.DriverService;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import cz.cvut.fel.ear.carstatus.util.Permissions;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/driver")
public class DriverController {
    private final DriverService driverService;
    private final RoadTripService roadTripService;

    private final Logger logger;

    @Autowired
    public DriverController(DriverService driverService, RoadTripService roadTripService, Logger logger) {
        this.driverService = driverService;
        this.roadTripService = roadTripService;
        this.logger = logger;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver getSpecificDriver(@PathVariable Integer id) {
        final Driver driver = driverService.find(id);
        if (driver == null) {
            throw NotFoundException.create("Driver", id);
        }
        return driver;
    }

    @GetMapping(value = "/{id}/roadtrips",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Roadtrip> getSpecificDriversRoadtrips(@PathVariable Integer id) {
        final Driver driver = driverService.find(id);
        if (driver == null) {
            logger.log("Driver with id:"+id+" was not found", null);
            throw NotFoundException.create("Driver", id);
        }
        return driverService.getAllRoadtripsMadeByDriver(id);
    }

    @GetMapping(value = "/last",produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver getActiveDriver() {
        return roadTripService.lastDriver();
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Driver> getDrivers() {
        return driverService.findAll();
    }

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeDriver(@RequestBody Driver driver) {
        driverService.remove(driver);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateDriver(@RequestBody Driver driver) {
        driverService.remove(driver);
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addDriver(@RequestBody(required = false) Driver driver) {
        if (!Permissions.eligibleToDrive(driver)) {
            throw new ValidationException("This user is too young to drive");
        }
        driverService.persist(driver);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", driver.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}

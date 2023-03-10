package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.service.DriverService;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rest/driver")
public class DriverController {
    private final DriverService driverService;
    private final RoadTripService roadTripService;
    private static final String DRIVER = "Driver with ID: ";
    private final Logger logger;

    @Autowired
    public DriverController(DriverService driverService, RoadTripService roadTripService, Logger logger) {
        this.driverService = driverService;
        this.roadTripService = roadTripService;
        this.logger = logger;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver getSpecificDriver(@PathVariable Integer id) {
        final Driver driver = driverService.find(id);
        if (driver == null) {
            logger.log( DRIVER+ id + " was not found.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver was not found");
        }
        logger.log(DRIVER + id + " was found.", ELoggerLevel.INFO);
        return driver;
    }

    @GetMapping(value = "/{id}/roadtrips",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Roadtrip> getSpecificDriversRoadtrips(@PathVariable Integer id) {
        final Driver driver = driverService.find(id);
        if (driver == null) {
            logger.log(DRIVER+id+" was not found", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver was not found");
        }
        return driverService.getAllRoadtripsMadeByDriver(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/last",produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver getActiveDriver() {
        return roadTripService.lastDriver();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Driver> getDrivers() {
        return driverService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDriver(@PathVariable Integer id) {
        final Driver driverToRemove = driverService.find(id);
        if(driverToRemove != null && driverToRemove.getId() == null){
            logger.log("Tried to delete driver without providing its ID.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete driver without providing its ID.");
        }
        else if (driverToRemove != null && roadTripService.lastDriver() == driverToRemove) {
            logger.log("Tried to delete last driver, action is aborted.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete last driver, action is aborted.");
        }
        if(driverToRemove != null){
            driverService.remove(driverToRemove);
        }
        else {
            logger.log("Tried to delete driver with not existing id.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete driver with not existing id.");
        }
    }
}

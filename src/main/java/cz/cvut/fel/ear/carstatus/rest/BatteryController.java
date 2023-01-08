package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.dto.BatteryDTO;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.EarException;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.exception.PersistenceException;
import cz.cvut.fel.ear.carstatus.exception.UnchangeableException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rest/battery")
public class BatteryController {

    private static final Logger logger = new Logger();
    private final BatteryService batteryService;
    private static final String WAS_ABORTED = "this is why the action was aborted.";

    @Autowired
    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Battery getSpecificBattery(@PathVariable Integer id) {
        final Battery battery = batteryService.find(id);
        if (battery == null) {
            logger.log("Battery with ID: " + id + " was not found.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Battery Not Found");
        }
        logger.log("Battery with ID: " + id + " was found.", ELoggerLevel.INFO);
        return battery;

    }

    @GetMapping(value = "/current",produces = MediaType.APPLICATION_JSON_VALUE)
    public Battery getCurrentBattery() {
        return batteryService.getCurrentBattery();
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Battery> getAllBatteries() {
        return batteryService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBattery(@PathVariable Integer id) {
        final Battery batteryToRemove = batteryService.find(id);
        if(batteryToRemove != null && batteryToRemove.getId() == null){
            logger.log("Tried to delete battery without providing its ID.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete battery without providing its ID.");
        }
        else if (batteryToRemove != null && batteryService.getCurrentBattery().getId().equals(batteryToRemove.getId())) {
            logger.log("Tried to delete battery which was in usage.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete battery which was in usage.");
        }
        if(batteryToRemove != null && batteryToRemove.getId() > 0 && batteryService.find(batteryToRemove.getId()) != null){
            batteryService.deleteBattery(batteryToRemove);
        }
        else {
            logger.log("Tried to delete battery with not existing id.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete battery with not existing id.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/")
    public void updateBattery(@RequestBody BatteryDTO batteryDTO) {
        Battery battery = new Battery();
        battery.setInUsage(batteryDTO.isInUsage());
        battery.setCapacity(batteryDTO.getCapacity());
        battery.setCondition(batteryDTO.getCondition());
        if(battery.getId() == null){
            logger.log("Tried to update battery without providing its ID.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to update battery without providing its ID.");
        }
        if (batteryService.getCurrentBattery().getId().equals(battery.getId())) {
            logger.log("Tried to update battery which was in usage.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to update battery which was in usage.");
        }
        else if(battery.isInUsage()){
            logger.log("Tried to update battery and set its usage to true, which leads to unstable behaviour." +
                    WAS_ABORTED, ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to update battery and set its usage to true, which is unstable behaviour.");
        }
        if(this.getAllBatteries().size() >= battery.getId() && battery.getId() > 0 && !battery.isInUsage() &&
                batteryService.find(battery.getId()) != null){
            batteryService.updateBattery(battery);
        }
        else {
            logger.log("Tried to update battery with not existing id.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to update battery with not existing id.");
        }
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBattery(@RequestBody BatteryDTO batteryDTO) {
        Battery battery = new Battery();
        battery.setInUsage(batteryDTO.isInUsage());
        battery.setCapacity(batteryDTO.getCapacity());
        battery.setCondition(batteryDTO.getCondition());
        if(battery.getCapacity() < 0 || battery.getCondition() < 0){
            logger.log("Tried to create battery with negative condition or capacity, action is aborted.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to create battery with negative condition or capacity, action is aborted.");
        }
        else if(battery.getCapacity() > 0 || battery.getCondition() > 0){
            logger.log("Tried to create battery with condition or capacity above 100%, action is aborted.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to create battery with negative condition or capacity above 100%, action is aborted.");
        }
        if(battery.isInUsage()){
            logger.log("Tried to create battery and set its usage to true, which leads to unstable behaviour," +
                    WAS_ABORTED, ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to create battery and set its usage to true, which is unstable behaviour.");
        }
        else if(battery.getId() == null || (battery.getId() != null && battery.getId() > this.getAllBatteries().size())){
            batteryService.createNewBattery(battery);
            final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", battery.getId());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        else {
            logger.log("Cannot create battery with existing id.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot create battery with existing id.");
        }
    }

    @PreAuthorize("hasRole('MECHANIC')")
    @PostMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeCurrentBattery(@RequestBody BatteryDTO batteryDTO) {
        Battery battery = new Battery();
        battery.setCondition(batteryDTO.getCondition());
        battery.setCapacity(batteryDTO.getCapacity());
        battery.setInUsage(batteryDTO.isInUsage());
        if (!batteryService.changeCurrentBattery(battery)) {
            logger.log("Tried to change current battery to broken battery.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to change current battery to broken battery.");
        }
    }

    @PutMapping(value = "/charge")
    public void chargeCurrentBattery() {
        batteryService.chargeBattery();
    }
}

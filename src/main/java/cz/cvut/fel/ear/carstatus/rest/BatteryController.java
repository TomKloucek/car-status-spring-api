package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.exception.UnchangeableException;
import cz.cvut.fel.ear.carstatus.exception.UndeletableException;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/battery")
public class BatteryController {

    private final BatteryService batteryService;

    @Autowired
    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Battery getSpecificBattery(@PathVariable Integer id) {
        final Battery battery = batteryService.find(id);
        if (battery == null) {
            throw NotFoundException.create("Category", id);
        }
        return battery;

    }

    @GetMapping(value = "/current",produces = MediaType.APPLICATION_JSON_VALUE)
    public Battery getCurrentBattery() {
        return batteryService.getCurrentBattery();
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Battery> getAllBatteries() {
        return batteryService.findAll();
    }

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeBattery(@RequestBody Battery battery) {
        if (battery.isInUsage()) {
            throw new UndeletableException("Tried to delete battery which was in usage");
        }
        batteryService.deleteBattery(battery);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateBattery(@RequestBody Battery battery) {
        if (battery.isInUsage()) {
            throw new UndeletableException("Tried to update battery which was in usage");
        }
        batteryService.updateBattery(battery);
    }


    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBattery(@RequestBody(required = false) Battery battery) {
        batteryService.createNewBattery(battery);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", battery.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void changeCurrentBattery(@RequestBody(required = false) Battery battery) {
        if (!batteryService.changeCurrentBattery(battery)) {
            throw new UnchangeableException("Tried to change current battery to broken battery");
        }
    }



}

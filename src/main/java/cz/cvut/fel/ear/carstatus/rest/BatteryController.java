package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Battery> getAllBatteries() {
        return batteryService.findAll();
    }

}

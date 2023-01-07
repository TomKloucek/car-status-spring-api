package cz.cvut.fel.ear.carstatus.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.exception.PersistenceException;
import cz.cvut.fel.ear.carstatus.exception.UnchangeableException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import cz.cvut.fel.ear.carstatus.service.CarcheckService;
import cz.cvut.fel.ear.carstatus.service.MechanicService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/carcheck")
public class CarcheckController {

    private static final Logger logger = new Logger();
    private final CarcheckService carcheckService;
    private final MechanicService mechanicService;

    @Autowired
    public CarcheckController(CarcheckService carcheckService, MechanicService mechanicService) {
        this.carcheckService = carcheckService;
        this.mechanicService = mechanicService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Carcheck getSpecificCarcheck(@PathVariable Integer id) {
        final Carcheck carcheck = carcheckService.find(id);
        if (carcheck == null) {
            logger.log("Car check with ID: " + id + " was not found.", ELoggerLevel.ERROR);
            throw NotFoundException.create("Carcheck", id);
        }
        logger.log("Car check with ID: " + id + " was found.", ELoggerLevel.ERROR);
        return carcheck;
    }

    @GetMapping(value = "/mechanic/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Carcheck> getAllCarcheckMadeByMechanic(@PathVariable Integer id) {
        final Mechanic mechanic = mechanicService.find(id);
        if (mechanic == null) {
            throw NotFoundException.create("Mechanic", id);
        }
        return carcheckService.getCarchecksMadeByMechanic(mechanic);

    }

    @GetMapping(value = "/last",produces = MediaType.APPLICATION_JSON_VALUE)
    public Carcheck getLastCarcheck() {
        return carcheckService.getLastCarcheck();
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Carcheck> getCarchecks() {
        return carcheckService.findAll();
    }

}

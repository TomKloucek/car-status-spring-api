package cz.cvut.fel.ear.carstatus.rest;


import cz.cvut.fel.ear.carstatus.dto.TyreDTO;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.*;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.TyreService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/tyre")
public class TyreController {

    private static final Logger logger = new Logger();
    private final TyreService tyreService;

    @Autowired
    public TyreController(TyreService tyreService) {
        this.tyreService = tyreService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Tyre getSpecificTyre(@PathVariable Integer id) {
        final Tyre tyre = tyreService.find(id);

        if (tyre == null) {
            logger.log("Tyre with ID: " + id + " was not found.", ELoggerLevel.ERROR);
            throw NotFoundException.create("Tyre", id);
        }
        logger.log("Tyre with ID: " + id + " was found.", ELoggerLevel.INFO);
        return tyre;

    }

    @GetMapping(value = "/current",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tyre> getCurrentTyres() {
        return tyreService.getCurrentTyres();
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tyre> getAllTyres() {
        return tyreService.findAll();
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/inflate/{id}")
    public void inflateTyre(@PathVariable Integer id) {
        tyreService.inflateTyre(id);
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/inflate-at-position/{position}")
    public void inflateTyreAtPosition(@PathVariable Integer position) {
        tyreService.inflateTyreAtPosition(position);
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/inflate")
    public void inflateTyres() {
        tyreService.inflateTyres();
    }

    @DeleteMapping(value = "/{id}")
    public void removeTyre(@PathVariable Integer id) {
        final Tyre tyreToRemove = tyreService.find(id);
        if (tyreToRemove == null) {
            logger.log("Tried to delete tyre which does not exist.", ELoggerLevel.ERROR);
            throw new UndeletableException("Tried to delete tyre which does not exist.");
        }
        else if(tyreToRemove.isInUsage()){
            logger.log("Tried to delete tyre which is in usage, action aborted.", ELoggerLevel.ERROR);
            throw new UndeletableException("Tried to delete tyre  which is in usage, action aborted.");
        }
        else {
            tyreService.deleteTyre(tyreToRemove);
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addTyre(@RequestBody(required = false) TyreDTO tyreDTO) {
        Tyre tyre = new Tyre();
        tyre.setPressure(tyreDTO.getPressure());
        tyre.setCondition(tyreDTO.getCondition());
        tyre.setInUsage(tyreDTO.isInUsage());
        tyre.setPosition(tyreDTO.getPosition());
        if(tyre.getPosition() < 1 || tyre.getPosition() > 4){
            logger.log("Tried to create tyre with pointless position, action is aborted.", ELoggerLevel.ERROR);
            throw new EarException("Tried to create tyre with pointless position, action is aborted.");
        }
        if(tyre.getPressure() < 0 || tyre.getCondition() < 0){
            logger.log("Tried to create tyre with negative pressure or condition, action is aborted.", ELoggerLevel.ERROR);
            throw new EarException("Tried to create tyre with negative pressure or condition, action is aborted.");
        }
        else if(tyre.getPressure() > 40 || tyre.getCondition() > 100){
            logger.log("Tried to create tyre with pointless pressure or condition, action is aborted.", ELoggerLevel.ERROR);
            throw new EarException("Tried to create tyre with pointless pressure or condition, action is aborted.");
        }
        if(tyre.isInUsage()){
            logger.log("Tried to create tyre and set its usage to true, which leads to unstable behaviour," +
                    " this is why the action was aborted.", ELoggerLevel.ERROR);
            throw new UnchangeableException("Tried to create tyre and set its usage to true, which leads to unstable behaviour," +
                    " this is why the action was aborted.");
        }
        else if(tyre.getId() == null || (tyre.getId() != null && tyre.getId() > this.getAllTyres().size())){
            tyreService.createNewTyre(tyre);
            final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", tyre.getId());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        else {
            logger.log("Cannot create tyre with existing id.", ELoggerLevel.ERROR);
            throw new PersistenceException("Cannot create tyre with existing id.");
        }
    }
}

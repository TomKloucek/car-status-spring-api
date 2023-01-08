package cz.cvut.fel.ear.carstatus.rest;


import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.exception.UnchangeableException;
import cz.cvut.fel.ear.carstatus.exception.UndeletableException;
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

    private final TyreService tyreService;

    @Autowired
    public TyreController(TyreService tyreService) {
        this.tyreService = tyreService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Tyre getSpecificTyre(@PathVariable Integer id) {
        final Tyre tyre = tyreService.find(id);
        if (tyre == null) {
            throw NotFoundException.create("Tyre", id);
        }
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
    @PutMapping(value = "/inflate/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void inflateTyre(@PathVariable Integer id) {
        tyreService.inflateTyre(id);
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/inflate-at-position/{position}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void inflateTyreAtPosition(@PathVariable Integer position) {
        tyreService.inflateTyreAtPosition(position);
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/inflate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void inflateTyres() {
        tyreService.inflateTyres();
    }

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeTyre(@RequestBody Tyre tyre) {
        if (tyre.isInUsage()) {
            throw new UndeletableException("Tried to delete tyre which was in usage");
        }
        tyreService.deleteTyre(tyre);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTyre(@RequestBody Tyre tyre) {
        if (tyre.isInUsage()) {
            throw new UndeletableException("Tried to update tyre which was in usage");
        }
        tyreService.updateTyre(tyre);
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addTyre(@RequestBody(required = false) Tyre tyre) {
        tyreService.createNewTyre(tyre);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", tyre.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void changeCurrentTyre(@RequestBody(required = false) Tyre tyre) {
        if (!tyreService.changeCurrentTyre(tyre)) {
            throw new UnchangeableException("Tried to change tyre with pressure lower than minimal threshold");
        }
    }
}

package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
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
            throw NotFoundException.create("Carcheck", id);
        }
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

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeCarcheck(@RequestBody Carcheck carcheck) {
        carcheckService.deleteCarcheck(carcheck);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCarcheck(@RequestBody Carcheck carcheck) {
        carcheckService.updateCarcheck(carcheck);
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addCarcheck(@RequestBody(required = false) Carcheck carcheck) {
        carcheckService.createNewCarcheck(carcheck);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", carcheck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}

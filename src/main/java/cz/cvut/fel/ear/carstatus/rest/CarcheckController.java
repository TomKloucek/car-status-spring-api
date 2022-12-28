package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.CarcheckService;
import cz.cvut.fel.ear.carstatus.service.MechanicService;
import cz.cvut.fel.ear.carstatus.service.TyreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/carcheck")
public class CarcheckController {

    private final CarcheckService charcheckService;
    private final MechanicService mechanicService;

    @Autowired
    public CarcheckController(CarcheckService carcheckService, MechanicService mechanicService) {
        this.charcheckService = carcheckService;
        this.mechanicService = mechanicService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Carcheck getSpecificCarcheck(@PathVariable Integer id) {
        final Carcheck carcheck = charcheckService.find(id);
        if (carcheck == null) {
            throw NotFoundException.create("Carcheck", id);
        }
        return carcheck;
    }

    @GetMapping(value = "/mechanic/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Carcheck> getAllCarcheckMadeByMechanic(@PathVariable Integer id) {
        final Mechanic mechanic = mechanicService.find(id);
        return charcheckService.getCarchecksMadeByMechanic(mechanic);

    }

    @GetMapping(value = "/last",produces = MediaType.APPLICATION_JSON_VALUE)
    public Carcheck getLastCarcheck() {
        return charcheckService.getLastCarcheck();
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Carcheck> getCarchecks() {
        return charcheckService.findAll();
    }

}

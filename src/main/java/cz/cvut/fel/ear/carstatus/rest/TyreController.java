package cz.cvut.fel.ear.carstatus.rest;


import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import cz.cvut.fel.ear.carstatus.service.TyreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

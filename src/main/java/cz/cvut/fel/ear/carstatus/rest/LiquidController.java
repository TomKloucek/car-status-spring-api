package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.LiquidService;
import cz.cvut.fel.ear.carstatus.service.TyreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/liquid")
public class LiquidController {
    private final LiquidService liquidService;

    @Autowired
    public LiquidController(LiquidService liquidService) {
        this.liquidService = liquidService;
    }

    @GetMapping(value = "/cooling",produces = MediaType.APPLICATION_JSON_VALUE)
    public Liquid getCoolingLiquid() {
        return liquidService.find("cooling");
    }

    @GetMapping(value = "/braking",produces = MediaType.APPLICATION_JSON_VALUE)
    public Liquid getBrakingLiquid() {
        return liquidService.find("braking");
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Liquid> getAllLiquids() {
        return liquidService.findAll();
    }
}

package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.service.SeatService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/tyre")
public class SeatController {
    private final SeatService seatService;
    private final Logger logger;

   @Autowired
    public SeatController(SeatService seatService, Logger logger) {
        this.seatService = seatService;
       this.logger = logger;
   }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/up", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addVerticalPosition() {
        return seatService.addVerticalPosition() ? "{"+JSONObject.toString("data","Successfully lifted your seat")+"}" : "{"+JSONObject.toString("err","Your seat is now in maximum height")+"}";
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/left", produces = MediaType.APPLICATION_JSON_VALUE)
    public String subtractHorizontalPosition() {
        return seatService.subtractHorizontalPosition() ? "{"+JSONObject.toString("data","Successfully moved your seat left")+"}" : "{"+JSONObject.toString("err","Your seat is now in maximum left")+"}";
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/right", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addHorizontalPosition() {
        return seatService.addHorizontalPosition() ? "{"+JSONObject.toString("data","Successfully moved your seat right")+"}" : "{"+JSONObject.toString("err","Your seat is now in maximum right position")+"}";
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC')")
    @PutMapping(value = "/down", produces = MediaType.APPLICATION_JSON_VALUE)
    public String subtractVerticalPosition() {
        return seatService.subtractVerticalPosition() ? "{"+JSONObject.toString("data","Successfully lowered your seat")+"}" : "{"+JSONObject.toString("err","Your seat is now in maximum down position")+"}";
    }

}

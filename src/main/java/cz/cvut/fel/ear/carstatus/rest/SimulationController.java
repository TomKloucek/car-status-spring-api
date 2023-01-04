package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.service.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/simulation")
public class SimulationController {

    private final Simulation simulation;
    private final Logger logger;

    @Autowired
    public SimulationController(Simulation simulation, Logger logger) {
        this.simulation = simulation;
        this.logger = logger;
    }

    @PutMapping(value = "/{number}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateNTimes(@PathVariable Integer number) {
        for (int i = 0; i < number; i++) {
            simulation.generateOneRoadTrip();
        }
        logger.log(SecurityContextHolder.getContext().getAuthentication().getPrincipal()+" generated "+number+" roadtrips using API");
    }

    @PutMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateOne() {
        simulation.generateOneRoadTrip();
        logger.log(SecurityContextHolder.getContext().getAuthentication().getPrincipal()+" generated one roadtrip using API");
    }

}

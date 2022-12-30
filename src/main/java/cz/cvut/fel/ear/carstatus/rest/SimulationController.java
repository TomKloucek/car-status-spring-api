package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.service.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/simulation")
public class SimulationController {

    private final Simulation simulation;

    @Autowired
    public SimulationController(Simulation simulation) {
        this.simulation = simulation;
    }

    @PutMapping(value = "/{number}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateNTimes(@PathVariable Integer number) {
        for (int i = 0; i < number; i++) {
            simulation.generateOneRoadTrip();
        }
    }

    @PutMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateOne() {
        simulation.generateOneRoadTrip();
    }

}

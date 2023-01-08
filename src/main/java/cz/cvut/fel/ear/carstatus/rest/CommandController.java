package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ECommand;
import cz.cvut.fel.ear.carstatus.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/command")
public class CommandController {

    private final SimulationService simulation;

    @Autowired
    public CommandController(SimulationService simulation) {
        this.simulation = simulation;
    }

    @PutMapping(value = "/driver",produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeCommandToDriver() {
        simulation.setCommand(ECommand.DRIVER);
        return "Changed command successfully to driver";
    }

    @PutMapping(value = "/road",produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeCommandToRoad() {
        simulation.setCommand(ECommand.ROAD);
        return "Changed command successfully to road";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/execute", produces = MediaType.APPLICATION_JSON_VALUE)
    public String executeCommand() {
        simulation.executeCommand();
        return "Successfully executed command";
    }

}

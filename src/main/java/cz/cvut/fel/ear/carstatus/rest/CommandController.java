package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.commands.GenerateDriverCommand;
import cz.cvut.fel.ear.carstatus.enums.ECommand;
import cz.cvut.fel.ear.carstatus.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
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
    public void changeCommandToDriver() {
        simulation.setCommand(ECommand.DRIVER);
    }

    @PutMapping(value = "/road",produces = MediaType.APPLICATION_JSON_VALUE)
    public void changeCommandToRoad() {
        simulation.setCommand(ECommand.ROAD);
    }

    @PutMapping(value = "/execute",produces = MediaType.APPLICATION_JSON_VALUE)
    public void executeCommand() {
        simulation.executeCommand();
    }

}
package cz.cvut.fel.ear.carstatus.commands;

import com.github.javafaker.Faker;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.interfaces.ICommand;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.service.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateRoadCommand implements ICommand {
    private final Faker start;
    private final Faker end;
    private final RoadService roadService;
    private final Logger logger;
    private final Random rnd;

    @Autowired
    public GenerateRoadCommand(RoadService roadService, Logger logger) {
        this.roadService = roadService;
        this.logger = logger;
        this.start = new Faker();
        this.end = new Faker();
        this.rnd = new Random();
    }

    @Override
    public void execute() {
        String cityOne = start.address().cityName();
        String cityTwo = end.address().cityName();
        int roadLength = rnd.nextInt(500);
        Road from = new Road();
        from.setStartingPoint(cityOne);
        from.setEndPoint(cityTwo);
        from.setLength(roadLength);
        roadService.persist(from);
        logger.log("road with id:"+from.getId(), ELoggerLevel.DEBUG);
        Road to = new Road();
        to.setStartingPoint(cityTwo);
        to.setEndPoint(cityOne);
        to.setLength(roadLength);
        roadService.persist(to);
        logger.log("road with id:"+to.getId(), ELoggerLevel.DEBUG);
    }
}

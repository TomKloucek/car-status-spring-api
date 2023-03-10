package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rest/roadtrip")
public class RoadtripController {

    private static final Logger logger = new Logger();
    private final RoadTripService roadtripService;

    @Autowired
    public RoadtripController(RoadTripService roadtripService) {
        this.roadtripService = roadtripService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Roadtrip getSpecificRoadtrip(@PathVariable Integer id) {
        final Roadtrip roadtrip = roadtripService.find(id);
        if (roadtrip == null) {
            logger.log("Road trip with ID: " + id + " was not found.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Road trip was not found.");
        }
        logger.log("Road trip with ID: " + id + " was found.", ELoggerLevel.INFO);
        return roadtrip;
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Roadtrip> getRoadtrips() {
        return roadtripService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRoadtrip(@PathVariable Integer id) {
        final Roadtrip roadTripToRemove = roadtripService.find(id);
        if(roadTripToRemove == null){
            logger.log("Tried to delete road trip with not existing id.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete road trip with not existing id.");
        }
        else {
            roadtripService.remove(roadTripToRemove);
        }
    }
}

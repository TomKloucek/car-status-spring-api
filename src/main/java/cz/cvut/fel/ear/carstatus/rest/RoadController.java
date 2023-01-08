package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.exception.UnchangeableException;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Carcheck;
import cz.cvut.fel.ear.carstatus.model.Mechanic;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.service.RoadService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/road")
public class RoadController {

    private static final Logger logger = new Logger();
    private final RoadService roadService;

    @Autowired
    public RoadController(RoadService roadService) {
        this.roadService = roadService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Road getSpecificRoad(@PathVariable Integer id) {
        final Road road = roadService.find(id);
        if (road == null) {
            logger.log("Road with ID: " + id + " was not found.", ELoggerLevel.ERROR);
            throw NotFoundException.create("Road", id);
        }
        logger.log("Road with ID: " + id + " was found.", ELoggerLevel.ERROR);
        return road;
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Road> getRoads() {
        return roadService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRoad(@PathVariable Integer id) {
        final Road roadToRemove = roadService.find(id);
        if(roadToRemove == null){
            logger.log("Tried to delete road with not existing id.", ELoggerLevel.ERROR);
            throw new UnchangeableException("Tried to delete road with not existing id.");
        }
        else {
            roadService.remove(roadToRemove);
        }
    }
}

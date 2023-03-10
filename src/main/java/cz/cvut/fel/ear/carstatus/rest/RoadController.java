package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.dto.RoadDTO;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.service.RoadService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Road getSpecificRoad(@PathVariable Integer id) {
        final Road road = roadService.find(id);
        if (road == null) {
            logger.log("Road with ID: " + id + " was not found.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Road not found");
        }
        logger.log("Road with ID: " + id + " was found.", ELoggerLevel.ERROR);
        return road;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Road> getRoads() {
        return roadService.findAll();
    }


    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoad(@RequestBody RoadDTO roadDTO) {
        Road road = roadService.find(roadDTO.getId());
        road.setEndPoint(roadDTO.getEndPoint());
        road.setLength(roadDTO.getLength());
        road.setStartingPoint(roadDTO.getStartingPoint());
        roadService.update(road);
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addRoad(@RequestBody(required = false) RoadDTO roadDTO) {
        Road road = new Road();
        road.setLength(roadDTO.getLength());
        road.setStartingPoint(roadDTO.getStartingPoint());
        road.setEndPoint(roadDTO.getEndPoint());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", road.getId());
        if (road.getStartingPoint() != null && road.getEndPoint() != null) {
            if(road.getLength() < 0){
                logger.log("Tried to create road with negative length, action is aborted.", ELoggerLevel.ERROR);
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to create road with negative length, action is aborted.");
            }
            roadService.persist(road);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
    }
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRoad (@PathVariable Integer id){
        final Road roadToRemove = roadService.find(id);
        if (roadToRemove == null) {
            logger.log("Tried to delete road with not existing id.", ELoggerLevel.ERROR);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Tried to delete road with not existing id.");
        } else {
            roadService.remove(roadToRemove);
        }
    }
}


package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.dto.RoadDTO;
import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
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

    private final RoadService roadService;

    @Autowired
    public RoadController(RoadService roadService) {
        this.roadService = roadService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Road getSpecificRoad(@PathVariable Integer id) {
        final Road road = roadService.find(id);
        if (road == null) {
            throw NotFoundException.create("Road", id);
        }
        return road;
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Road> getRoads() {
        return roadService.findAll();
    }

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeRoad(@RequestBody Road road) {
        roadService.remove(road);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoad(@RequestBody Road road) {
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
            roadService.persist(road);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
    }
}

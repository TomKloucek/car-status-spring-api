package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.exception.NotFoundException;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import cz.cvut.fel.ear.carstatus.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/roadtrip")
public class RoadtripController {

    private final RoadTripService roadtripService;

    @Autowired
    public RoadtripController(RoadTripService roadtripService) {
        this.roadtripService = roadtripService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Roadtrip getSpecificRoadtrip(@PathVariable Integer id) {
        final Roadtrip roadtrip = roadtripService.find(id);
        if (roadtrip == null) {
            throw NotFoundException.create("Roadtrip", id);
        }
        return roadtrip;
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Roadtrip> getRoadtrips() {
        return roadtripService.findAll();
    }

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeRoadtrip(@RequestBody Roadtrip roadtrip) {
        roadtripService.remove(roadtrip);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoadtrip(@RequestBody Roadtrip roadtrip) {
        roadtripService.update(roadtrip);
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addRoadtrip(@RequestBody(required = false) Roadtrip roadtrip) {
        roadtripService.persist(roadtrip);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", roadtrip.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}

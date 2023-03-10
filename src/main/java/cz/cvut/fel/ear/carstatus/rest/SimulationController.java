package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.load_files.LoadSimulationFromCSV;
import cz.cvut.fel.ear.carstatus.load_files.LoadSimulationFromJSON;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/simulation")
public class SimulationController {

    private final SimulationService simulation;
    private final CarStateService carStateService;
    private final Logger logger;
    private final LoadSimulationFromCSV csvLoader;
    private final LoadSimulationFromJSON jsonLoader;

    @Autowired
    public SimulationController(SimulationService simulation, CarStateService carStateService, Logger logger, LoadSimulationFromCSV csvLoader, LoadSimulationFromJSON jsonLoader) {
        this.simulation = simulation;
        this.carStateService = carStateService;
        this.logger = logger;
        this.csvLoader = csvLoader;
        this.jsonLoader = jsonLoader;
    }

    @PutMapping(value = "/{number}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateNTimes(@PathVariable Integer number) {
        boolean carBrokeDown = false;
        int tripsGenerated = 0;
        for (int i = 0; i < number; i++) {
            carStateService.updateMalfunctionality();
            if(carStateService.isPossibleToDrive()) {
                simulation.generateOneRoadTrip();
                carStateService.updateMalfunctionality();
                tripsGenerated += 1;
            }
            else {
                carBrokeDown = true;
                break;
            }
        }
        if(carBrokeDown){
            logger.log("Car was able to drive " + tripsGenerated + " trips before it broke down.", ELoggerLevel.INFO);
        }
        logger.log(SecurityContextHolder.getContext().getAuthentication().getName()+" generated "+tripsGenerated+" road trips using API", ELoggerLevel.INFO);
    }

    private String handleLoadingFile(String headers, String content, String contentType) {
        if (headers.equals("application/json")) {
            Pair<Boolean, String> result = jsonLoader.readSimulationFromFile(content);
            if (Boolean.TRUE.equals(result.getFirst())) {
                return result.getSecond();
            }
            return result.getSecond();
        } else if (headers.equals("text/csv")) {
            Pair<Boolean, String> result = csvLoader.readSimulationFromFile(content);
            if (Boolean.TRUE.equals(result.getFirst())) {
                return result.getSecond();
            }
            return result.getSecond();
        } else {
            logger.log("Upload of file failed: you tried to upload unsupported file type: " + contentType + ".", ELoggerLevel.ERROR);
            return "Upload of file failed: you tried to upload unsupported file type: " + contentType;
        }
    }

    @PostMapping(value = "/")
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String content = new String(file.getBytes());
                String headers = file.getContentType();
                assert headers != null;
                if (!headers.isEmpty()) {
                    return handleLoadingFile(headers,content,file.getContentType());
                }
            } catch (Exception e) {
                logger.log("You failed to upload file => " + e.getMessage()+".", ELoggerLevel.ERROR);
                return "You failed to upload file => " + e.getMessage();
            }
        } else {
            logger.log("Upload failed because the file was empty.", ELoggerLevel.ERROR);
            return "You failed to upload because the file was empty.";
        }
        return null;
    }

    @PutMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateOne() {
        carStateService.updateMalfunctionality();
        if(carStateService.isPossibleToDrive()) {
            simulation.generateOneRoadTrip();
            logger.log(SecurityContextHolder.getContext().getAuthentication().getName()+" generated one road trip using API", ELoggerLevel.INFO);
        }
        else {
            logger.log(SecurityContextHolder.getContext().getAuthentication().getName()+" user was not able to generate road trip, because car is broken.", ELoggerLevel.INFO);
        }

    }

}

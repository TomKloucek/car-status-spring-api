package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.DataClass;
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
        for (int i = 0; i < number; i++) {
            if(carStateService.isPossibleToDrive()) {
                simulation.generateOneRoadTrip();
                carStateService.updateMalfunctionality();
            }
            else {
                carBrokeDown = true;
                break;
            }
        }
        if(carBrokeDown){
            logger.log("Car was able to drive " + DataClass.getInstance().getNumberOfSimulationMethodCalls() + " trips before it broke down.", ELoggerLevel.INFO);
        }
        logger.log(SecurityContextHolder.getContext().getAuthentication().getPrincipal()+" generated "+DataClass.getInstance().getNumberOfSimulationMethodCalls()+" roadtrips using API", ELoggerLevel.INFO);
    }

    @PostMapping(value = "/")
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String content = new String(file.getBytes());
                if (file.getContentType().equals("application/json")) {
                    Pair<Boolean, String> result = jsonLoader.readSimulationFromFile(content);
                    if (Boolean.TRUE.equals(result.getFirst())) {
                        return result.getSecond();
                    }
                    return result.getSecond();
                } else if (file.getContentType().equals("text/csv")) {
                    Pair<Boolean, String> result = csvLoader.readSimulationFromFile(content);
                    if (Boolean.TRUE.equals(result.getFirst())) {
                        return result.getSecond();
                    }
                    return result.getSecond();
                } else {
                    logger.log("Upload of file failed: you tried to upload unsupported file type: "+file.getContentType()+".", ELoggerLevel.ERROR);
                    return "Upload of file failed: you tried to upload unsupported file type: "+file.getContentType();
                }

            } catch (Exception e) {
                logger.log("You failed to upload file => " + e.getMessage()+".", ELoggerLevel.ERROR);
                return "You failed to upload file => " + e.getMessage();
            }
        } else {
            logger.log("Upload failed because the file was empty.", ELoggerLevel.ERROR);
            return "You failed to upload because the file was empty.";
        }
    }


    @PutMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateOne() {
        simulation.generateOneRoadTrip();
        logger.log(SecurityContextHolder.getContext().getAuthentication().getPrincipal()+" generated one roadtrip using API", ELoggerLevel.INFO);
    }

}

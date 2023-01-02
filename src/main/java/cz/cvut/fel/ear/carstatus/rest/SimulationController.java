package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.load_files.LoadSimulationFromCSV;
import cz.cvut.fel.ear.carstatus.load_files.LoadSimulationFromJSON;
import cz.cvut.fel.ear.carstatus.service.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/rest/simulation")
public class SimulationController {

    private final Simulation simulation;
    private final LoadSimulationFromCSV csvLoader;
    private final LoadSimulationFromJSON jsonLoader;

    @Autowired
    public SimulationController(Simulation simulation, LoadSimulationFromCSV csvLoader, LoadSimulationFromJSON jsonLoader) {
        this.simulation = simulation;
        this.csvLoader = csvLoader;
        this.jsonLoader = jsonLoader;
    }

    @PutMapping(value = "/{number}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateNTimes(@PathVariable Integer number) {
        for (int i = 0; i < number; i++) {
            simulation.generateOneRoadTrip();
        }
    }

    @PostMapping(value = "/")
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                if (file.getContentType().equals("json")) {
                    if (jsonLoader.readSimulationFromFile(Arrays.toString(bytes))) {
                      return "Loading of simulation file was succesfull";
                    }
                    return "Something wrong happened";
                } else if (file.getContentType().equals("csv")) {
                    if (csvLoader.readSimulationFromFile(Arrays.toString(bytes))) {
                        return "Loading of simulation file was succesfull";
                    }
                    return "Something wrong happened";
                } else {
                    return "Upload of file failed: you tried to upload unsupported file type";
                }

            } catch (Exception e) {
                return "You failed to upload file => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }


    @PutMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public void simulateOne() {
        simulation.generateOneRoadTrip();
    }

}

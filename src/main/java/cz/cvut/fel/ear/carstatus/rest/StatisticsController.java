package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.builders.StatisticsBuilder;
import cz.cvut.fel.ear.carstatus.builders.StatisticsFilterBuilder;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;
import cz.cvut.fel.ear.carstatus.service.DriverService;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/statistics")
public class StatisticsController {

    private final DriverService driverService;
    private final StatisticsFactory factory;
    private final RoadTripService roadTripService;
    private final Logger logger;


    @Autowired
    public StatisticsController(DriverService driverService, StatisticsFactory factory, RoadTripService roadTripService, Logger logger) {
        this.driverService = driverService;
        this.factory = factory;
        this.roadTripService = roadTripService;
        this.logger = logger;
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getStatistics(@RequestBody JSONObject filter) {
        StatisticsFilterBuilder builder = new StatisticsFilterBuilder();
        try {
                if (filter.get("from") != null) {
                    builder = builder.from(Date.valueOf(LocalDate.parse((String) filter.get("from"))));
                }
                if (filter.get("to") != null) {
                    builder = builder.to(Date.valueOf(LocalDate.parse((String) filter.get("to"))));
                }
                builder = builder.driver(driverService.find((int) filter.get("driver")));
                builder = builder.start((String) filter.get("start"));
                builder = builder.finish((String) filter.get("finish"));
                DataClass.getInstance().incrementNumberOfStatisticsGenerated();
                return factory.getStatistics(builder.build());
        } catch (Exception e) {
            logger.log(e.toString(), ELoggerLevel.ERROR);
            return "Filter provided was not possible to accept";
        }
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getStatisticsSpecific(@PathVariable Integer id) {
        try {
            List<Roadtrip> filtered = new ArrayList<>();
            filtered.add(roadTripService.find(id));
            return new StatisticsBuilder().numberOfKm(filtered).numberOfRoadtrips(filtered).averageSpeed(filtered).maxSpeed(filtered).build();
        } catch (Exception e) {
            logger.log(e.toString(), ELoggerLevel.ERROR);
            return "Statistics with this specific id dont exists";
        }
    }
}

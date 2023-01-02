package cz.cvut.fel.ear.carstatus.rest;

import cz.cvut.fel.ear.carstatus.service.DriverService;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import cz.cvut.fel.ear.carstatus.statistics.Statistics;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFactory;
import cz.cvut.fel.ear.carstatus.statistics.StatisticsFilter;
import cz.cvut.fel.ear.carstatus.builders.StatisticsFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/rest/statistics")
public class StatisticsController {

    private final DriverService driverService;
    private final StatisticsFactory factory;


    @Autowired
    public StatisticsController(DriverService driverService, StatisticsFactory factory) {
        this.driverService = driverService;
        this.factory = factory;
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public Statistics getStatistics(@RequestBody Object filter) {
        String[] filterParams = {"from","to","driver","start","finish"};
        StatisticsFilterBuilder builder = new StatisticsFilterBuilder();
        for (String param: filterParams) {
            try {
                switch (param) {
                    case "from":
                        builder.from((Date) filter.getClass().getDeclaredField("from").get(this));
                        break;
                    case "to":
                        builder.to((Date) filter.getClass().getDeclaredField("to").get(this));
                        break;
                    case "driver":
                        builder.driver(driverService.find((Integer) filter.getClass().getDeclaredField("driver").get(this)));
                        break;
                    case "start":
                        builder.start((String) filter.getClass().getDeclaredField("start").get(this));
                        break;
                    case "finish":
                        builder.finish((String) filter.getClass().getDeclaredField("finish").get(this));
                        break;
                    default:
                        break;
                }
            } catch (Exception ignored) {}
        }
        StatisticsFilter statFilter = new StatisticsFilterBuilder().build();
        return factory.getStatistics(statFilter);
    }
}

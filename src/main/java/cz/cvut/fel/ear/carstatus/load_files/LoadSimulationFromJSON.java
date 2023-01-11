package cz.cvut.fel.ear.carstatus.load_files;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Road;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import cz.cvut.fel.ear.carstatus.service.DriverService;
import cz.cvut.fel.ear.carstatus.service.RoadService;
import cz.cvut.fel.ear.carstatus.service.TyreService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class LoadSimulationFromJSON implements ILoadSimulationFile {
    private final Logger logger = new Logger();
    final DriverService driverService;
    final RoadService roadService;
    final BatteryService batteryService;
    final TyreService tyreService;

    private static final String CONDITION = "condition";

    @Autowired
    public LoadSimulationFromJSON(DriverService driverService, RoadService roadService, BatteryService batteryService, TyreService tyreService) {
        this.driverService = driverService;
        this.roadService = roadService;
        this.batteryService = batteryService;
        this.tyreService = tyreService;
    }

    @Transactional
    @Override
    public Pair<Boolean,String> readSimulationFromFile(String fileContent) {
        try {
            Object file = JSONValue.parse(fileContent);

            JSONArray jsonArray = (JSONArray)file;

            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject)object;
                if (isDriver(jsonObject)) {
                    Driver driver = new Driver();
                    driver.setUsername((String) jsonObject.get("username"));
                    driver.setPassword((String) jsonObject.get("password"));
                    driver.setLastName((String) jsonObject.get("lastname"));
                    driver.setFirstName((String) jsonObject.get("firstname"));
                    driver.setBirthDate(Date.valueOf(LocalDate.parse((String) jsonObject.get("birthdate"))));
                    driverService.persist(driver);
                } else if (isBattery(jsonObject)) {
                    Battery battery = new Battery();
                    battery.setCondition((int) (long) jsonObject.get(CONDITION));
                    battery.setCapacity((int) (long) jsonObject.get("capacity"));
                    batteryService.createNewBattery(battery);
                } else if (isRoad(jsonObject)) {
                    Road road = new Road();
                    road.setLength((int) (long) jsonObject.get("length"));
                    road.setEndPoint((String) jsonObject.get("endpoint"));
                    road.setStartingPoint((String) jsonObject.get("startingpoint"));
                    roadService.persist(road);
                } else if (isTyre(jsonObject)) {
                    Tyre tyre = new Tyre();
                    tyre.setPosition(Integer.parseInt((String) jsonObject.get("position")));
                    tyre.setPressure((long) jsonObject.get("pressure"));
                    tyre.setCondition((int) (long) jsonObject.get(CONDITION));
                    tyreService.createNewTyre(tyre);
                }
            }
            DataClass.getInstance().incrementNumberOfJSONFilesLoaded();
            logger.log("All object that fulfilled rules were added to application from JSON.", ELoggerLevel.INFO);
            return Pair.of(true,"All object that fulfilled rules were added to application");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return Pair.of(true,sw.toString());
        }
    }

    private boolean isDriver(JSONObject object) {
        return (object.containsKey("birthdate") && object.containsKey("firstname") && object.containsKey("lastname") && object.containsKey("password") && object.containsKey("username"));
    }

    private boolean isRoad(JSONObject object) {
        return (object.containsKey("startingpoint") && object.containsKey("endpoint") && object.containsKey("length"));
    }

    private boolean isBattery(JSONObject object) {
        return (object.containsKey("capacity") && object.containsKey(CONDITION));
    }

    private boolean isTyre(JSONObject object) {
        return (object.containsKey("position") && object.containsKey(CONDITION) && object.containsKey("pressure"));
    }
}

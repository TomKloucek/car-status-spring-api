package cz.cvut.fel.ear.carstatus.load_files;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.interfaces.ILoadSimulationFile;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.service.*;
import cz.cvut.fel.ear.carstatus.util.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class LoadSimulationFromCSV implements ILoadSimulationFile {

    final DriverService driverService;
    final RoadService roadService;
    final BatteryService batteryService;
    final TyreService tyreService;

    @Autowired
    public LoadSimulationFromCSV(DriverService driverService, RoadService roadService, BatteryService batteryService, TyreService tyreService) {
        this.driverService = driverService;
        this.roadService = roadService;
        this.batteryService = batteryService;
        this.tyreService = tyreService;
    }

    @Override
    @Transactional
    public Pair<Boolean, String> readSimulationFromFile(String fileContent) {
        String res = "";
        try {
            String[] lines = fileContent.split("\n");
            String[] keywords = lines[0].split(",");
            if (isDriver(lines[0])) {
                for (int i = 1; i < lines.length; i++) {
                    String[] values = lines[i].split(",");
                    Driver driver = new Driver();
                    driver.setRole(Role.DRIVER);
                    driver.setBirthDate(Date.valueOf(LocalDate.parse(values[java.util.Arrays.asList(keywords).indexOf("birthdate")])));
                    driver.setFirstName(values[java.util.Arrays.asList(keywords).indexOf("firstname")]);
                    driver.setLastName(values[java.util.Arrays.asList(keywords).indexOf("lastname")]);
                    driver.setPassword(values[java.util.Arrays.asList(keywords).indexOf("password")]);
                    driver.setUsername(values[java.util.Arrays.asList(keywords).indexOf("username")]);
                    res = "drivers";
                    driverService.persist(driver);
                }
            } else if (isRoad(lines[0])) {
                for (int i = 1; i < lines.length; i++) {
                    String[] values = lines[i].split(",");
                    Road road = new Road();
                    road.setStartingPoint(values[java.util.Arrays.asList(keywords).indexOf("startingpoint")]);
                    road.setEndPoint(values[java.util.Arrays.asList(keywords).indexOf("endpoint")]);
                    road.setLength(Integer.parseInt(values[java.util.Arrays.asList(keywords).indexOf("length")]));
                    res = "roads";
                    roadService.persist(road);
                }
            } else if (isBattery(lines[0])) {
                for (int i = 1; i < lines.length; i++) {
                    String[] values = lines[i].split(",");
                    Battery battery = new Battery();
                    battery.setCapacity(Integer.parseInt(values[java.util.Arrays.asList(keywords).indexOf("capacity")]));
                    battery.setCondition(Integer.parseInt(values[java.util.Arrays.asList(keywords).indexOf("condition")]));
                    res = "batteries";
                    batteryService.createNewBattery(battery);
                }
            } else if (isTyre(lines[0])) {
                for (int i = 1; i < lines.length; i++) {
                    String[] values = lines[i].split(",");
                    Tyre tyre = new Tyre();
                    tyre.setPressure(Double.parseDouble(values[java.util.Arrays.asList(keywords).indexOf("pressure")]));
                    tyre.setCondition(Integer.parseInt(values[java.util.Arrays.asList(keywords).indexOf("condition")]));
                    tyre.setPosition(Integer.parseInt(values[java.util.Arrays.asList(keywords).indexOf("position")]));
                    res = "tyres";
                    tyreService.createNewTyre(tyre);
                }
            } else {
                return Pair.of(false,"Provided file were not of type { driver, tyre, battery, road }");
            }
            DataClass.getInstance().incrementNumberOfCSVFilesLoaded();
            return Pair.of(true,res + " were successfully loaded to application");
        } catch (Exception e) {
            return Pair.of(false,e.toString());
        }
    }

    private boolean isDriver(String firstLine) {
        Set<String> driver = new HashSet<>();
        Set<String> compare = new HashSet<>();
        driver.add("birthdate");
        driver.add("firstname");
        driver.add("lastname");
        driver.add("password");
        driver.add("username");
        Collections.addAll(compare, firstLine.split(","));
        return driver.equals(compare);
    }

    private boolean isRoad(String firstLine) {
        Set<String> road = new HashSet<>();
        Set<String> compare = new HashSet<>();
        road.add("startingpoint");
        road.add("endpoint");
        road.add("length");
        Collections.addAll(compare, firstLine.split(","));
        return road.equals(compare);
    }

    private boolean isBattery(String firstLine) {
        Set<String> battery = new HashSet<>();
        Set<String> compare = new HashSet<>();
        battery.add("capacity");
        battery.add("condition");
        Collections.addAll(compare, firstLine.split(","));
        return battery.equals(compare);
    }

    private boolean isTyre(String firstLine) {
        Set<String> tyre = new HashSet<>();
        Set<String> compare = new HashSet<>();
        tyre.add("position");
        tyre.add("condition");
        tyre.add("pressure");
        Collections.addAll(compare, firstLine.split(","));
        return tyre.equals(compare);
    }
}

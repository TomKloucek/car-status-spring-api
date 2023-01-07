package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.commands.GenerateDriverCommand;
import cz.cvut.fel.ear.carstatus.commands.GenerateRoadCommand;
import cz.cvut.fel.ear.carstatus.enums.ECommand;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.interfaces.ICommand;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SimulationService {

    private Random rnd;

    private final Logger logger = new Logger();

    private DriverService driverService;

    private RoadService roadService;

    private LiquidService liquidService;

    private TyreService tyreService;

    private RoadTripService roadTripService;

    private RoadPathService roadPathService;
    private BatteryService batteryService;
    private CarStateService carStateService;
    private ICommand command;

    private final GenerateDriverCommand driverCommand;
    private final GenerateRoadCommand roadCommand;

    @Autowired
    public SimulationService(DriverService ds, RoadTripService rts, RoadPathService rps, LiquidService ls, RoadService rs, BatteryService bs, GenerateDriverCommand driverCommand, GenerateRoadCommand roadCommand, CarStateService carStateService,
    TyreService tyreservice) {
        this.driverCommand = driverCommand;
        this.roadCommand = roadCommand;
        this.rnd = new Random();
        this.roadPathService = rps;
        this.driverService = ds;
        this.roadTripService = rts;
        this.liquidService = ls;
        this.roadService = rs;
        this.tyreService = tyreservice;
        this.batteryService = bs;
        this.carStateService = carStateService;
    }

    public List<Road> generateRoads(int length) {
        DataClass.getInstance().incrementNumberOfRoadsGenerated();
        List<Road> roads = roadService.findAll();
        List<Road> result = new ArrayList<>();
        result.add(roads.get(rnd.nextInt(roads.size()-1)));
        while (result.size() < length) {
            DataClass.getInstance().incrementNumberOfRoadsAdded();
            String end = result.get(result.size()-1).getEndPoint();
            for (Road r : roads) {
                if (r.getStartingPoint().equals(end)) {
                    result.add(r);
                    logger.log("New road from "+r.getStartingPoint()+ " to " + r.getEndPoint() + " was generated.", ELoggerLevel.INFO);
                }
            }
        }
        return result;
    }

    public void generateOneRoadTrip() {
        logger.log("--------------------------------------------",ELoggerLevel.INFO);
        logger.log("Start of road trip.", ELoggerLevel.INFO);
        DataClass.getInstance().incrementNumberOfSimulationMethodCalls();
        List<Driver> drivers = driverService.findAll();
        Driver driver = drivers.get(rnd.nextInt(drivers.size()));
        int tripLength = rnd.nextInt(5) + 1;
        List<Road> roads = this.generateRoads(tripLength);
        Roadtrip roadtrip = new Roadtrip();
        roadtrip.setWithMalfunction(false);
        roadtrip.setMaxSpeed(rnd.nextInt(150) + 50);
        logger.log("Max speed was set to : " + roadtrip.getMaxSpeed() +".", ELoggerLevel.INFO);
        List<Roadpath> roadpathList = roads.stream()
                .map(road -> {
                    Roadpath roadpath = new Roadpath();
                    roadpath.setRoadtrip(roadtrip);
                    logger.log("Road trip was set.", ELoggerLevel.INFO);
                    roadpath.setRoad(road);
                    logger.log("Road trip road was set.", ELoggerLevel.INFO);
                    roadpath.setAverageSpeed(rnd.nextInt((roadtrip.getMaxSpeed() - 25) + 1) + 25);
                    logger.log("Road trip average speed was set to: " + roadpath.getAverageSpeed() + ".", ELoggerLevel.INFO);
                    return roadpath;
                })
                .collect(Collectors.toList());
        roadtrip.setRoadpathList(roadpathList);
        Date date = new Date();
        roadtrip.setFinished(date);
        logger.log("Road trip length was: "+ tripLength + ".", ELoggerLevel.INFO);
        logger.log("Road trip finish date was set to: "+ date.toString() + ".", ELoggerLevel.INFO);
        roadtrip.setDriver(driver);
        updateCarLiquids(tripLength);
        updateBattery(tripLength);
        updateTyres(tripLength);
        roadTripService.persist(roadtrip);
        for (Roadpath rp : roadpathList) {
            roadPathService.persist(rp);
        }
        logger.log("New road trip was generated.", ELoggerLevel.INFO);
        logger.log("Car drove one more trip.",ELoggerLevel.INFO);
        logger.log("Road trip finished.", ELoggerLevel.INFO);
        logger.log("--------------------------------------------",ELoggerLevel.INFO);

    }

    private void updateCarLiquids(int roadLength) {
        for (Liquid l : liquidService.findAll()) {
            if(l.getType().equals("braking") && l.getLevel()-roadLength*2 > 0){
                l.setLevel(l.getLevel()-roadLength*2);
            }
            else if (l.getType().equals("cooling") && l.getLevel()-roadLength*1 > 0){
                l.setLevel(l.getLevel()-roadLength*1);
            }
            liquidService.update(l);
        }
    }

    private void updateBattery(int roadLength) {
        Battery battery = batteryService.getCurrentBattery();
        battery.setCondition((int) (battery.getCondition()-(roadLength*0.25)));
        int subtractedAmount = roadLength*rnd.nextInt(20);
        while (battery.getCapacity()-subtractedAmount < 0){
            subtractedAmount = roadLength*rnd.nextInt(20);
        }
        if(battery.getCapacity()-subtractedAmount > 0){
            battery.setCapacity((battery.getCapacity()-subtractedAmount));
        }
        batteryService.updateBattery(battery);
    }
    private void updateTyres(int roadLength) {
        List <Tyre> tyres = tyreService.getCurrentTyres();
        for(Tyre tyre : tyres){
            tyre.setCondition((int) (tyre.getCondition()-(roadLength*0.0001)*rnd.nextInt(20)));
            tyre.setPressure((tyre.getPressure()-(roadLength*0.002)*rnd.nextInt(40)));
            tyreService.updateTyre(tyre);
        }
    }

    public void setCommand(ECommand command) {
        logger.log("Command was set to: " +command.name() +".",ELoggerLevel.INFO);
        switch (command) {
            case ROAD:
                this.command = roadCommand;
                break;
            case DRIVER:
                this.command = driverCommand;
                break;
            default:
                break;
        }
    }

    public void executeCommand() {
        logger.log("Command was executed.",ELoggerLevel.INFO);
        command.execute();
    }
}

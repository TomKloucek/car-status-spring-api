package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.commands.GenerateDriverCommand;
import cz.cvut.fel.ear.carstatus.commands.GenerateRoadCommand;
import cz.cvut.fel.ear.carstatus.enums.ECommand;
import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.commands.ICommand;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
    }

    @Transactional
    public List<Road> generateRoads(int length) {
        DataClass.getInstance().incrementNumberOfRoadsGenerated();
        List<Road> roads = roadService.findAll();
        List<Road> result = new ArrayList<>();
        Road cur = roads.get(rnd.nextInt(roads.size()-1));
        result.add(cur);
        while (result.size() < length) {
            DataClass.getInstance().incrementNumberOfRoadsAdded();
            for (Road r : roads) {
                if (r.getStartingPoint().equals(cur.getEndPoint())) {
                    result.add(r);
                    cur = r;
                    Collections.shuffle(roads);
                    logger.log("New road from "+r.getStartingPoint()+ " to " + r.getEndPoint() + " was generated.", ELoggerLevel.INFO);
                }
            }
        }
        return result;
    }

    @Transactional
    public void generateOneRoadTrip() {
        Battery batteryInUsage;
        batteryInUsage = batteryService.findAll().stream().filter(Battery::isInUsage).findFirst().orElse(null);
        if (batteryInUsage != null){
            batteryService.changeCurrentBattery(batteryInUsage);
        }
        logger.log("--------------------------------------------",ELoggerLevel.INFO);
        logger.log("Start of road trip.", ELoggerLevel.INFO);
        DataClass.getInstance().incrementNumberOfSimulationMethodCalls();
        List<Driver> drivers = driverService.findAll();
        Driver driver = drivers.get(rnd.nextInt(drivers.size()));
        int tripLength = rnd.nextInt(10) + 1;
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

    @Transactional
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

    @Transactional
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
    @Transactional
    private void updateTyres(int roadLength) {
        List <Tyre> tyres = tyreService.getCurrentTyres();
        for(Tyre tyre : tyres){
            tyre.setCondition((int) (tyre.getCondition()-(roadLength*0.0001)*rnd.nextInt(20)));
            tyre.setPressure((tyre.getPressure()-(roadLength*0.002)*rnd.nextInt(40)));
            tyreService.updateTyre(tyre);
        }
    }
    @Transactional
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

    @Transactional
    public void executeCommand() {
        if(this.command == null){
            logger.log("Because command was not set, it was set to a road command by default.",ELoggerLevel.INFO);
            this.command = roadCommand;
        }
        logger.log("Command was executed.",ELoggerLevel.INFO);
        command.execute();
    }
}

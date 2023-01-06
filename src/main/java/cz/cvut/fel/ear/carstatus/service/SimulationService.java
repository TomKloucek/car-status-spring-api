package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.DataClass;
import cz.cvut.fel.ear.carstatus.commands.GenerateDriverCommand;
import cz.cvut.fel.ear.carstatus.commands.GenerateRoadCommand;
import cz.cvut.fel.ear.carstatus.enums.ECommand;
import cz.cvut.fel.ear.carstatus.interfaces.ICommand;
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

    private DriverService driverService;

    private RoadService roadService;

    private LiquidService liquidService;

    private RoadTripService roadTripService;

    private RoadPathService roadPathService;
    private BatteryService batteryService;
    private CarStateService carStateService;
    private ICommand command;

    private final GenerateDriverCommand driverCommand;
    private final GenerateRoadCommand roadCommand;

    @Autowired
    public SimulationService(DriverService ds, RoadTripService rts, RoadPathService rps, LiquidService ls, RoadService rs, BatteryService bs, GenerateDriverCommand driverCommand, GenerateRoadCommand roadCommand, CarStateService carStateService) {
        this.driverCommand = driverCommand;
        this.roadCommand = roadCommand;
        this.rnd = new Random();
        this.roadPathService = rps;
        this.driverService = ds;
        this.roadTripService = rts;
        this.liquidService = ls;
        this.roadService = rs;
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
                }
            }
        }
        return result;
    }

    public void generateOneRoadTrip() {
        if(carStateService.isPossibleToDrive()) {
            DataClass.getInstance().incrementNumberOfSimulationMethodCalls();
            List<Driver> drivers = driverService.findAll();
            Driver driver = drivers.get(rnd.nextInt(drivers.size()));
            int tripLength = rnd.nextInt(5) + 1;
            List<Road> roads = this.generateRoads(tripLength);
            Roadtrip roadtrip = new Roadtrip();
            roadtrip.setWithMalfunction(false);
            roadtrip.setMaxSpeed(rnd.nextInt(150) + 50);
            List<Roadpath> roadpathList = roads.stream()
                    .map(road -> {
                        Roadpath roadpath = new Roadpath();
                        roadpath.setRoadtrip(roadtrip);
                        roadpath.setRoad(road);
                        roadpath.setAverageSpeed(rnd.nextInt((roadtrip.getMaxSpeed() - 25) + 1) + 25);
                        return roadpath;
                    })
                    .collect(Collectors.toList());
            roadtrip.setRoadpathList(roadpathList);
            roadtrip.setFinished(new Date());
            roadtrip.setDriver(driver);
            updateCarLiquids(tripLength);
            updateBattery(tripLength);
            roadTripService.persist(roadtrip);
            for (Roadpath rp : roadpathList) {
                roadPathService.persist(rp);
            }
        } else {
            // TODO logger a neco udelat treba to predelat na boolean a kdyztak vratit nejakej vysledek
        }
    }

    private void updateCarLiquids(int roadLength) {
        for (Liquid l : liquidService.findAll()) {
            l.setLevel(l.getLevel()-roadLength*2);
            liquidService.update(l);
        }
    }

    private void updateBattery(int roadLength) {
        Battery battery = batteryService.getCurrentBattery();
        battery.setCondition((int) (battery.getCondition()-(roadLength*0.25)));
        battery.setCapacity((int) (battery.getCapacity()-(roadLength*0.7)));
        batteryService.updateBattery(battery);
    }

    public void setCommand(ECommand command) {
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
        command.execute();
    }
}

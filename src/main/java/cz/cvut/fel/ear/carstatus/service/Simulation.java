package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class Simulation {

    private Random rnd;

    private DriverService driverService;

    private RoadService roadService;

    private LiquidService liquidService;

    private RoadTripService roadTripService;

    private RoadPathService roadPathService;
    private BatteryService batteryService;
    private TyreService tyreService;

    private CarStateService carStateService;

    @Autowired
    public Simulation(DriverService ds, RoadTripService rts, RoadPathService rps, LiquidService ls, RoadService rs, BatteryService bs,CarStateService service, TyreService tyreService) {
        this.rnd = new Random();
        this.roadPathService = rps;
        this.driverService = ds;
        this.roadTripService = rts;
        this.liquidService = ls;
        this.roadService = rs;
        this.batteryService = bs;
        carStateService = service;
        this.tyreService = tyreService;
    }

    public List<Road> generateRoads(int length) {
        List<Road> roads = roadService.findAll();
        List<Road> result = new ArrayList<>();
        result.add(roads.get(rnd.nextInt(roads.size()-1)));
        while (result.size() < length) {
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
        carStateService.updateMalfunctionality();
        if (carStateService.isPossibleToDrive()) {
            List<Driver> drivers = driverService.findAll();
            Driver driver = drivers.get(rnd.nextInt(drivers.size()));
            int tripLength = rnd.nextInt(5) + 1;
            List<Road> roads = this.generateRoads(tripLength);
            Roadtrip roadtrip = new Roadtrip();
            roadtrip.setWithMalfunction(false);
            roadtrip.setMaxSpeed(rnd.nextInt(150) + 50);
            List<Roadpath> roadpathList = new ArrayList<>();
            for (Road road : roads) {
                Roadpath roadpath = new Roadpath();
                roadpath.setRoadtrip(roadtrip);
                roadpath.setRoad(road);
                roadpath.setAverageSpeed(rnd.nextInt((roadtrip.getMaxSpeed() - 25) + 1) + 25);
                roadpathList.add(roadpath);
            }
            roadtrip.setRoadpathList(roadpathList);
            roadtrip.setFinished(new Date());
            roadtrip.setDriver(driver);
            updateCarLiquids(tripLength);
            updateBattery(tripLength);
            updateTyres(tripLength);
            roadTripService.persist(roadtrip);
            for (Roadpath rp : roadpathList) {
                roadPathService.persist(rp);
            }
            carStateService.updateMalfunctionality();
        } else {
            // TODO throw exception or something like that
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

    private void updateTyres(int roadLength) {
        List<Tyre> tyres = tyreService.getCurrentTyres();
        for(Tyre tyre : tyres){
            tyre.setPressure(tyre.getPressure() - roadLength*0.001);
            tyre.setCondition((int) (tyre.getPressure() - roadLength/1000));
        }
    }
}

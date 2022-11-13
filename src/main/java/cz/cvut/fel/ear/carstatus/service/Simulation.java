package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.dao.DriverDao;
import cz.cvut.fel.ear.carstatus.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    public Simulation(DriverService ds, RoadTripService rts, RoadPathService rps, LiquidService ls, RoadService rs) {
        this.rnd = new Random();
        this.roadPathService = rps;
        this.driverService = ds;
        this.roadTripService = rts;
        this.liquidService = ls;
        this.roadService = rs;
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
        List<Driver> drivers = driverService.findAll();
        Driver driver = drivers.get(rnd.nextInt(drivers.size()));
        int tripLength = rnd.nextInt(5)+1;
        List<Road> roads = this.generateRoads(tripLength);
        Roadtrip roadtrip = new Roadtrip();
        roadtrip.setWithMalfunction(false);
        roadtrip.setMaxSpeed(rnd.nextInt(150));
        List<Roadpath> roadpathList = new ArrayList<>();
        for (Road road : roads) {
            Roadpath roadpath = new Roadpath();
            roadpath.setRoadtrip(roadtrip);
            roadpath.setRoad(road);
            roadpathList.add(roadpath);
        }
        roadtrip.setRoadpathList(roadpathList);
        updateCarLiquids(tripLength);
        roadTripService.persist(roadtrip);
        for (Roadpath rp : roadpathList) {
            roadPathService.persist(rp);
        }
    }

    public void updateCarLiquids(int roadLength) {
        for (Liquid l : liquidService.findAll()) {
            l.setLevel(l.getLevel()-roadLength*2);
            liquidService.update(l);
            liquidService.persist(l);
        }
    }
}

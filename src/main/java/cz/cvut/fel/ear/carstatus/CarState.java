package cz.cvut.fel.ear.carstatus;

import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import cz.cvut.fel.ear.carstatus.service.LiquidService;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CarState {
    private static CarState _instance;
    List<IObserver> observers;
    private Battery battery;
    private List<Liquid> liquids;
    private List<Tyre> tyres;
    private List<Seat> seats;
    private Logger logger;
    private Driver currentDriver;
    private boolean isMalfunctioned;

    @Autowired
    BatteryService batteryService;

    @Autowired
    LiquidService liquidService;

    @Autowired
    RoadTripService roadTripService;

    private CarState() {
        this.battery = batteryService.getCurrentBattery();
        liquids = liquidService.findAll();
        currentDriver = roadTripService.lastDriver();
        this.observers = new ArrayList<>();

    }

    public static CarState getInstance() {
        if (_instance == null) {
            synchronized (CarState.class) {
                if (_instance == null) {
                    _instance = new CarState();
                }
            }
        }
        return _instance;
    }

}

package cz.cvut.fel.ear.carstatus;

import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import cz.cvut.fel.ear.carstatus.service.LiquidService;
import cz.cvut.fel.ear.carstatus.service.RoadTripService;
import cz.cvut.fel.ear.carstatus.service.TyreService;
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

    @Autowired
    TyreService tyreService;

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

    public boolean isPossibleToDrive() {
        return !isMalfunctioned;
    }

    public void updateMalfunctionality() {
        for (Liquid l: liquids) {
            if (l.checkWhetherIsBelowOrAtMinLevel()) {
                this.isMalfunctioned = true;
            }
        }
        if (!batteryService.batteryIsFunctional()) {
            this.isMalfunctioned = true;
        }
        if (tyreService.tyresAreFunctional()) {
            this.isMalfunctioned = true;
        }
    }

}

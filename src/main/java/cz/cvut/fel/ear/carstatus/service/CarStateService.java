package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarStateService {

    List<IObserver> observers;
    private Battery battery;
    private List<Liquid> liquids;
    private List<Tyre> tyres;
    private List<Seat> seats;
    private Logger logger;
    private Driver currentDriver;
    private boolean isMalfunctioned;

    private final BatteryService batteryService;

    private final LiquidService liquidService;

    private final RoadTripService roadTripService;

    private final TyreService tyreService;

    @Autowired
    public CarStateService(BatteryService batteryService, LiquidService liquidService, RoadTripService roadTripService, TyreService tyreService ) {
        this.batteryService = batteryService;
        this.liquidService = liquidService;
        this.roadTripService = roadTripService;
        this.tyreService = tyreService;
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

    public Battery getBattery() {
        return batteryService.getCurrentBattery();
    }

    public List<Liquid> getLiquids() {
        return liquidService.findAll();
    }

    public List<Tyre> getTyres() {
        return tyreService.getCurrentTyres();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Driver getCurrentDriver() {
        return currentDriver;
    }

    public boolean isMalfunctioned() {
        return isMalfunctioned;
    }
}

package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.LowTyrePressureDecorator;
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

    private final BatteryService batteryService;

    private final LiquidService liquidService;

    private final RoadTripService roadTripService;

    private final TyreService tyreService;
    private List<EMalfunction> malfunctions;
    private BaseDecorator notifyMalfunctions;

    @Autowired
    public CarStateService(BatteryService batteryService, LiquidService liquidService, RoadTripService roadTripService, TyreService tyreService ) {
        this.batteryService = batteryService;
        this.liquidService = liquidService;
        this.roadTripService = roadTripService;
        this.tyreService = tyreService;
    }

    public boolean isPossibleToDrive() {
        return malfunctions.isEmpty();
    }

    public BaseDecorator getNotifyMalfunctions() {
        for (EMalfunction malfunction : malfunctions) {
            switch (malfunction) {
                case LOWTYREPRESSURE:
                    notifyMalfunctions = new LowTyrePressureDecorator(notifyMalfunctions);
                    break;
            }
        }
        return notifyMalfunctions;
    }

    public void updateMalfunctionality() {
        for(IObserver observer : observers){
            EMalfunction malfunction = observer.update(this);
            if (malfunction != null) malfunctions.add(malfunction);
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

    public void notifyObservers(){
        for(IObserver observer : observers){
            observer.update(this);
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Driver getCurrentDriver() {
        return currentDriver;
    }
}

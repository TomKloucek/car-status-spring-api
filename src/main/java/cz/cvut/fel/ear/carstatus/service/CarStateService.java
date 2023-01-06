package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.*;
import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.malfunctions.*;
import cz.cvut.fel.ear.carstatus.notifications.Notifier;
import cz.cvut.fel.ear.carstatus.observers.*;
import cz.cvut.fel.ear.carstatus.rest.UserController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CarStateService {

    List<IObserver> observers;
    private Battery battery;
    private List<Liquid> liquids;
    private List<Tyre> tyres;
    private List<Seat> seats;
    private Logger logger;
    private Driver currentDriver;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserController.class);

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
        this.malfunctions = new ArrayList<>();
        this.notifyMalfunctions = new BaseDecorator(new Notifier());
        this.observers = new ArrayList<>();
        fillObservers();
        updateMalfunctionality();
    }

    public boolean isPossibleToDrive() {
        return malfunctions.isEmpty();
    }

    public BaseDecorator getNotifyMalfunctions() {
        notifyMalfunctions = new BaseDecorator(new Notifier());
        malfunctions
                .forEach(malfunction -> {
                    switch (malfunction) {
                        case LOWTYREPRESSURE:
                            notifyMalfunctions = new LowTyrePressureDecorator(notifyMalfunctions);
                            break;
                        case LOWBRAKINGLIQUID:
                            notifyMalfunctions = new LowBrakingLiquidDecorator(notifyMalfunctions);
                            break;
                        case LOWBATTERYCAPACITY:
                            notifyMalfunctions = new LowBatteryCapacityDecorator(notifyMalfunctions);
                            break;
                        case LOWBATTERYCONDITION:
                            notifyMalfunctions = new LowBatteryConditionDecorator(notifyMalfunctions);
                            break;
                        case LOWCOOLINGLIQUID:
                            notifyMalfunctions = new LowCoolingLiquidDecorator(notifyMalfunctions);
                            break;
                        case LOWTYRECONDITION:
                            notifyMalfunctions = new LowTyreConditionDecorator(notifyMalfunctions);
                            break;
                    }
                });
        return notifyMalfunctions;
    }

    public void updateMalfunctionality() {
        malfunctions = new ArrayList<>();
        observers.stream()
                .map(observer -> observer.update(this))
                .filter(Objects::nonNull)
                .forEach(malfunction -> malfunctions.add(malfunction));
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

    private void fillObservers() {
        this.observers.add(new LowTyreConditionObserver());
        this.observers.add(new LowBatteryCapacityObserver());
        this.observers.add(new LowTyrePressureObserver());
        this.observers.add(new LowBatteryConditionObserver());
        this.observers.add(new LowBrakingLiquidObserver());
        this.observers.add(new LowCoolingLiquidObserver());
    }
}

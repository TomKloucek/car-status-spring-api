package cz.cvut.fel.ear.carstatus.service;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.notifications.*;
import cz.cvut.fel.ear.carstatus.notifications.malfunctions.*;
import cz.cvut.fel.ear.carstatus.observers.IObserver;
import cz.cvut.fel.ear.carstatus.log.Logger;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.model.Seat;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.observers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CarStateService {

    List<IObserver> observers;
    private static final Logger logger = new Logger();

    private final BatteryService batteryService;

    private final LiquidService liquidService;

    private final SeatService seatService;

    private final TyreService tyreService;
    private List<EMalfunction> malfunctions;
    private BaseDecorator notifyMalfunctions;

    @Autowired
    public CarStateService(BatteryService batteryService, LiquidService liquidService, SeatService seatService, TyreService tyreService ) {
        this.batteryService = batteryService;
        this.liquidService = liquidService;
        this.seatService = seatService;
        this.tyreService = tyreService;
        this.malfunctions = new ArrayList<>();
        this.notifyMalfunctions = new BaseDecorator(new Notifier());
        this.observers = new ArrayList<>();
        fillObservers();
        updateMalfunctionality();
    }

    @Transactional
    public boolean isPossibleToDrive() {
        logger.log("Application checked if it is possible to drive the car.", ELoggerLevel.INFO);
        return malfunctions.isEmpty();
    }
    @Transactional
    public BaseDecorator getNotifyMalfunctions() {
        notifyMalfunctions = new BaseDecorator(new Notifier());
        malfunctions
                .forEach(malfunction -> {
                    switch (malfunction) {
                        case LOWTYREPRESSURE:
                            logger.log("Low tyre pressure was discovered.", ELoggerLevel.INFO);
                            notifyMalfunctions = new LowTyrePressureDecorator(notifyMalfunctions);
                            break;
                        case LOWBRAKINGLIQUID:
                            logger.log("Low braking liquid level was discovered.", ELoggerLevel.INFO);
                            notifyMalfunctions = new LowBrakingLiquidDecorator(notifyMalfunctions);
                            break;
                        case LOWBATTERYCAPACITY:
                            logger.log("Low battery capacity was discovered.", ELoggerLevel.INFO);
                            notifyMalfunctions = new LowBatteryCapacityDecorator(notifyMalfunctions);
                            break;
                        case LOWBATTERYCONDITION:
                            logger.log("Low battery condition was discovered.", ELoggerLevel.INFO);
                            notifyMalfunctions = new LowBatteryConditionDecorator(notifyMalfunctions);
                            break;
                        case LOWCOOLINGLIQUID:
                            logger.log("Low cooling liquid level was discovered.", ELoggerLevel.INFO);
                            notifyMalfunctions = new LowCoolingLiquidDecorator(notifyMalfunctions);
                            break;
                        case LOWTYRECONDITION:
                            logger.log("Low tyre condition was discovered.", ELoggerLevel.INFO);
                            notifyMalfunctions = new LowTyreConditionDecorator(notifyMalfunctions);
                            break;
                    }
                });
        return notifyMalfunctions;
    }

    @Transactional
    public void updateMalfunctionality() {
        malfunctions = new ArrayList<>();
        observers.stream()
                .map(observer -> observer.update(this))
                .filter(Objects::nonNull)
                .forEach(malfunction -> malfunctions.add(malfunction));
        logger.log("Malfunctions were updated.", ELoggerLevel.INFO);
    }

    @Transactional
    public Battery getBattery() {
        return batteryService.getCurrentBattery();
    }

    @Transactional
    public List<Liquid> getLiquids() {
        return liquidService.findAll();
    }

    @Transactional
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

    @PreAuthorize("hasAnyRole('DRIVER', 'MECHANIC', 'ADMIN')")
    public Seat getDriversSeat() {
        return seatService.getCurrentDriversSeat();
    }
}

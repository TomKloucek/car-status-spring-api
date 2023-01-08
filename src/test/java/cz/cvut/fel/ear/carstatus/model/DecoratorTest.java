package cz.cvut.fel.ear.carstatus.model;

import cz.cvut.fel.ear.carstatus.enums.ELoggerLevel;
import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.Notifier;
import cz.cvut.fel.ear.carstatus.notifications.malfunctions.*;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class DecoratorTest {

    @Test
    void decoratorMethodsAreAddedAfterCallingThem() {
        BaseDecorator notifyMalfunctions = new BaseDecorator(new Notifier());
        List<EMalfunction> malfunctions = new ArrayList<>();
        malfunctions.add(EMalfunction.LOWBATTERYCAPACITY);
        malfunctions.add(EMalfunction.LOWBRAKINGLIQUID);
        String result = getNotifyMalfunctions(malfunctions, notifyMalfunctions).sendMessage("");
        String resultString = "<p> </p><h3>MALFUNCTIONS:</h3><p color='red'>Low braking liquid level</p><p color='red'>Low battery capacity</p>";
        assertEquals(resultString, result);

    }

    public BaseDecorator getNotifyMalfunctions(List<EMalfunction> malfunctions, BaseDecorator notifyMalfunctions) {
        for (EMalfunction malfunction : malfunctions) {
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
        }
        return notifyMalfunctions;
    }
}


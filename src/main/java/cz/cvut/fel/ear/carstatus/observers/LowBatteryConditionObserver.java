package cz.cvut.fel.ear.carstatus.observers;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.util.Constants;

public class LowBatteryConditionObserver implements IObserver {
    @Override
    public EMalfunction update(CarStateService service) {
        Battery battery = service.getBattery();
        if(battery != null && battery.getCondition() < Constants.MINIMAL_BATTERY_CONDITION){
            return EMalfunction.LOWBATTERYCONDITION;
        }
        else return null;
    }
}

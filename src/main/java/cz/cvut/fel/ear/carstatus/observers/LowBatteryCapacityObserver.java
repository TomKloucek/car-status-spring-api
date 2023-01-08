package cz.cvut.fel.ear.carstatus.observers;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.util.Constants;

public class LowBatteryCapacityObserver implements IObserver {
    @Override
    public EMalfunction update(CarStateService service) {
        Battery battery = service.getBattery();
        if(battery != null && battery.getCapacity() < Constants.MINIMAL_BATTERY_CHARGE){
            return EMalfunction.LOWBATTERYCAPACITY;
        }
        else return null;
    }
}

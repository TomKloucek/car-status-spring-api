package cz.cvut.fel.ear.carstatus.observers;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.util.Constants;

public class LowBatteryCapacityObserver implements IObserver {
    @Override
    public EMalfunction update(CarStateService service) {
        if(service.getBattery().getCapacity() < Constants.MINIMAL_BATTERY_CHARGE){
            return EMalfunction.LOWBATTERYCAPACITY;
        }
        else return null;
    }
}

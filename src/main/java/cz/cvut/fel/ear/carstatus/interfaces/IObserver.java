package cz.cvut.fel.ear.carstatus.interfaces;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.service.CarStateService;

public interface IObserver {
    public EMalfunction update(CarStateService service);
}

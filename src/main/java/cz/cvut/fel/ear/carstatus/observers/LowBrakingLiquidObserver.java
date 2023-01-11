package cz.cvut.fel.ear.carstatus.observers;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.service.CarStateService;

import java.util.List;

public class LowBrakingLiquidObserver implements IObserver {
    @Override
    public EMalfunction update(CarStateService service) {
        List<Liquid> liquids = service.getLiquids();
        for(Liquid liquid : liquids){
            if(liquid.getType().equals("braking") && liquid.getLevel() < liquid.getMinLevel()){
                return EMalfunction.LOWBRAKINGLIQUID;
            }
            else {
                return null;
            }
        }
        return null;
    }
}

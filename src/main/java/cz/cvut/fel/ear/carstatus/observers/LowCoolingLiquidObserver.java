package cz.cvut.fel.ear.carstatus.observers;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.interfaces.IObserver;
import cz.cvut.fel.ear.carstatus.model.Liquid;
import cz.cvut.fel.ear.carstatus.service.CarStateService;

import java.util.List;

public class LowCoolingLiquidObserver implements IObserver {
    @Override
    public EMalfunction update(CarStateService service) {
        List<Liquid> liquids = service.getLiquids();
        for(Liquid liquid : liquids){
            if(liquid.getType().equals("cooling") && liquid.getLevel() < liquid.getMinLevel()){
                return EMalfunction.LOWCOOLINGLIQUID;
            }
        }
        return null;
    }
}

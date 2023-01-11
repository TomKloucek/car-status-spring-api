package cz.cvut.fel.ear.carstatus.observers;

import cz.cvut.fel.ear.carstatus.enums.EMalfunction;
import cz.cvut.fel.ear.carstatus.model.Tyre;
import cz.cvut.fel.ear.carstatus.service.CarStateService;
import cz.cvut.fel.ear.carstatus.util.Constants;

import java.util.List;

public class LowTyreConditionObserver implements IObserver {

    @Override
    public EMalfunction update(CarStateService service) {
        List<Tyre> tyres = service.getTyres();
        for(Tyre tyre : tyres){
            if(tyre.getCondition() < Constants.MINIMAL_TYRE_CONDITION){
                return EMalfunction.LOWTYRECONDITION;
            }
        }
        return null;
    }
}

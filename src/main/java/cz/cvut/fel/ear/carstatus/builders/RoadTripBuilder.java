package cz.cvut.fel.ear.carstatus.builders;

import cz.cvut.fel.ear.carstatus.model.Driver;
import cz.cvut.fel.ear.carstatus.model.Roadpath;
import cz.cvut.fel.ear.carstatus.model.Roadtrip;

import javax.swing.text.StyledEditorKit;
import java.util.List;

public class RoadTripBuilder {
    private Roadtrip result;

    public void reset() {
        this.result = null;
    }

    public void addDriver(Driver driver) {
        result.setDriver(driver);
    }

    public void addRoad(List<Roadpath> roads) {
        result.setRoadpathList(roads);
    }

    public void setMaxSpeed(int maxSpeed) {
        result.setMaxSpeed(maxSpeed);
    }

    public void setWithMalfunction(Boolean withMalfunction) {
        result.setWithMalfunction(withMalfunction);
    }

    public Roadtrip getResult() {
        return result;
    }
}

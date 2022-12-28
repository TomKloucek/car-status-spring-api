package cz.cvut.fel.ear.carstatus.liquids;

public abstract class Liquid {
    private int minimalLevel;
    private int liquidLevel;

    public boolean checkLevel() {
        if (liquidLevel < minimalLevel) {
            return false;
        }
        return true;
    }

}

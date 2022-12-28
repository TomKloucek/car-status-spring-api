package cz.cvut.fel.ear.carstatus;

public class DataClass {
    private int numberOfMethodCalls;
    private int numberOfLoggerCalls;
    private int numberOfSimulationMethodCalls;

    public DataClass(int numberOfMethodCalls) {
        this.numberOfMethodCalls = 0;
        this.numberOfLoggerCalls = 0;
        this.numberOfSimulationMethodCalls = 0;
    }

    public int getNumberOfMethodCalls() {
        return numberOfMethodCalls;
    }

    public int getNumberOfLoggerCalls() {
        return numberOfLoggerCalls;
    }

    public int getNumberOfSimulationMethodCalls() {
        return numberOfSimulationMethodCalls;
    }
}

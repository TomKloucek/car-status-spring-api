package cz.cvut.fel.ear.carstatus;

public class DataClass {
    private int numberOfLoadOfFileCalls;
    private int numberOfLoggerCalls;
    private int numberOfSimulationMethodCalls;

    public DataClass(int numberOfMethodCalls) {
        this.numberOfLoadOfFileCalls = 0;
        this.numberOfLoggerCalls = 0;
        this.numberOfSimulationMethodCalls = 0;
    }

    public int getNumberOfMethodCalls() {
        return numberOfLoadOfFileCalls;
    }

    public int getNumberOfLoggerCalls() {
        return numberOfLoggerCalls;
    }

    public int getNumberOfSimulationMethodCalls() {
        return numberOfSimulationMethodCalls;
    }
}

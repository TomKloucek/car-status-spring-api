package cz.cvut.fel.ear.carstatus;
public class DataClass {
    private static DataClass instance = null;
    private int numberOfMethodCalls;
    private int numberOfLoggerCalls;
    private int numberOfSimulationMethodCalls;

    private DataClass() {
    }

    public static DataClass getInstance() {
        if (instance == null) {
            instance = new DataClass();
        }
        return instance;
    }

    public int getNumberOfMethodCalls() {
        return numberOfMethodCalls;
    }

    public void setNumberOfMethodCalls(int numberOfMethodCalls) {
        this.numberOfMethodCalls = numberOfMethodCalls;
    }

    public int getNumberOfLoggerCalls() {
        return numberOfLoggerCalls;
    }

    public void setNumberOfLoggerCalls(int numberOfLoggerCalls) {
        this.numberOfLoggerCalls = numberOfLoggerCalls;
    }

    public int getNumberOfSimulationMethodCalls() {
        return numberOfSimulationMethodCalls;
    }

    public void setNumberOfSimulationMethodCalls(int numberOfSimulationMethodCalls) {
        this.numberOfSimulationMethodCalls = numberOfSimulationMethodCalls;
    }
}
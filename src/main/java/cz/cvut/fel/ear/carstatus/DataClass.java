package cz.cvut.fel.ear.carstatus;
public class DataClass {
    private int numberOfLoggerCalls;
    private int numberOfSimulationMethodCalls;
    private int numberOfDriversGenerated;
    private int numberOfRoadsGenerated;
    private int numberOfDriversAdded;
    private int numberOfRoadsAdded;
    private int numberOfBatteriesAdded;
    private int numberOfTyresAdded;

    private int numberOfBatteriesChanged;
    private int numberOfTyresChanged;
    private int numberOfCarchecksMade;
    private int numberOfStatisticsGenerated;
    private int numberOfChargingBatteries;
    private int numberOfTyresInflated;
    private int numberOfCoolingLiquidRefills;
    private int numberOfBrakingLiquidReffils;
    private int numberOfCSVFilesLoaded;
    private int numberOfJSONFilesLoaded;
    private int numberOfUsersRegistered;


    private DataClass() {
        numberOfLoggerCalls = 0;
        numberOfSimulationMethodCalls = 0;
        numberOfDriversGenerated = 0;
        numberOfRoadsGenerated = 0;
        numberOfDriversAdded = 0;
        numberOfRoadsAdded = 0;
        numberOfBatteriesAdded = 0;
        numberOfTyresAdded = 0;
        numberOfBatteriesChanged = 0;
        numberOfTyresChanged = 0;
        numberOfCarchecksMade = 0;

        numberOfStatisticsGenerated = 0;

        numberOfChargingBatteries = 0;

        numberOfTyresInflated = 0;
        numberOfCoolingLiquidRefills = 0;
        numberOfBrakingLiquidReffils = 0;
        numberOfCSVFilesLoaded = 0;
        numberOfJSONFilesLoaded = 0;
        numberOfUsersRegistered = 0;
    }

    private static final class InstanceHolder {
        private static final DataClass instance = new DataClass();
    }

    public static DataClass getInstance() {
        return InstanceHolder.instance;
    }


    public int getNumberOfDriversGenerated() {
        return numberOfDriversGenerated;
    }

    public void incrementNumberOfDriversGenerated() {
        this.numberOfDriversGenerated += 1;
    }

    public int getNumberOfRoadsGenerated() {
        return numberOfRoadsGenerated;
    }

    public void incrementNumberOfRoadsGenerated() {
        this.numberOfRoadsGenerated += 1;
    }

    public int getNumberOfDriversAdded() {
        return numberOfDriversAdded;
    }

    public void incrementNumberOfDriversAdded() {
        this.numberOfDriversAdded += 1;
    }

    public int getNumberOfRoadsAdded() {
        return numberOfRoadsAdded;
    }

    public void incrementNumberOfRoadsAdded() {
        this.numberOfRoadsAdded += 1;
    }

    public int getNumberOfBatteriesAdded() {
        return numberOfBatteriesAdded;
    }

    public void incrementNumberOfBatteriesAdded() {
        this.numberOfBatteriesAdded += 1;
    }

    public int getNumberOfTyresAdded() {
        return numberOfTyresAdded;
    }

    public void incrementNumberOfTyresAdded() {
        this.numberOfTyresAdded += 1;
    }

    public int getNumberOfBatteriesChanged() {
        return numberOfBatteriesChanged;
    }

    public void incrementNumberOfBatteriesChanged() {
        this.numberOfBatteriesChanged += 1;
    }

    public int getNumberOfTyresChanged() {
        return numberOfTyresChanged;
    }

    public void incrementNumberOfTyresChanged() {
        this.numberOfTyresChanged += 1;
    }

    public int getNumberOfCarchecksMade() {
        return numberOfCarchecksMade;
    }

    public void incrementNumberOfCarchecksMade() {
        this.numberOfCarchecksMade += 1;
    }

    public int getNumberOfStatisticsGenerated() {
        return numberOfStatisticsGenerated;
    }

    public void incrementNumberOfStatisticsGenerated() {
        this.numberOfStatisticsGenerated += 1;
    }

    public int getNumberOfChargingBatteries() {
        return numberOfChargingBatteries;
    }

    public void incrementNumberOfChargingBatteries() {
        this.numberOfChargingBatteries += 1;
    }

    public int getNumberOfTyresInflated() {
        return numberOfTyresInflated;
    }

    public void incrementNumberOfTyresInflated() {
        this.numberOfTyresInflated += 1;
    }

    public int getNumberOfCoolingLiquidRefills() {
        return numberOfCoolingLiquidRefills;
    }

    public void incrementNumberOfCoolingLiquidRefills() {
        this.numberOfCoolingLiquidRefills += 1;
    }

    public int getNumberOfBrakingLiquidReffils() {
        return numberOfBrakingLiquidReffils;
    }

    public void incrementNumberOfBrakingLiquidReffils() {
        this.numberOfBrakingLiquidReffils += 1;
    }

    public int getNumberOfCSVFilesLoaded() {
        return numberOfCSVFilesLoaded;
    }

    public void incrementNumberOfCSVFilesLoaded() {
        this.numberOfCSVFilesLoaded += 1;
    }

    public int getNumberOfJSONFilesLoaded() {
        return numberOfJSONFilesLoaded;
    }

    public void incrementNumberOfJSONFilesLoaded() {
        this.numberOfJSONFilesLoaded += 1;
    }

    public int getNumberOfUsersRegistered() {
        return numberOfUsersRegistered;
    }

    public void incrementNumberOfUsersRegistered() {
        this.numberOfUsersRegistered += 1;
    }

    public int getNumberOfLoggerCalls() {
        return numberOfLoggerCalls;
    }

    public void incrementNumberOfLoggerCalls() {
        this.numberOfLoggerCalls += 1;
    }

    public int getNumberOfSimulationMethodCalls() {
        return numberOfSimulationMethodCalls;
    }

    public void incrementNumberOfSimulationMethodCalls() {
        this.numberOfSimulationMethodCalls += 1;
    }
}
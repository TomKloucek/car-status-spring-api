package cz.cvut.fel.ear.carstatus;
public class DataClass {
    private static DataClass instance = null;
    private int numberOfMethodCalls;
    private int numberOfLoggerCalls;
    private int numberOfSimulationMethodCalls;
    private int numberOfDriversGenerated;
    private int numberOfRoadsGenerated;
    private int numberOfDriversAdded;
    private int numberOfRoadsAdded;
    private int numberOfBatteriesAdded;
    private int numberOfTyresAdded;
    private int numberOfRoadtripsGenerated;
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
        numberOfMethodCalls = 0;
        numberOfLoggerCalls = 0;
        numberOfSimulationMethodCalls = 0;
        numberOfDriversGenerated = 0;
        numberOfRoadsGenerated = 0;
        numberOfDriversAdded = 0;
        numberOfRoadsAdded = 0;
        numberOfBatteriesAdded = 0;
        numberOfTyresAdded = 0;
        numberOfRoadtripsGenerated = 0;
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

    public static DataClass getInstance() {
        if (instance == null) {
            instance = new DataClass();
        }
        return instance;
    }

    public static void setInstance(DataClass instance) {
        DataClass.instance = instance;
    }

    public int getNumberOfDriversGenerated() {
        return numberOfDriversGenerated;
    }

    public void setNumberOfDriversGenerated(int numberOfDriversGenerated) {
        this.numberOfDriversGenerated = numberOfDriversGenerated;
    }

    public int getNumberOfRoadsGenerated() {
        return numberOfRoadsGenerated;
    }

    public void setNumberOfRoadsGenerated(int numberOfRoadsGenerated) {
        this.numberOfRoadsGenerated = numberOfRoadsGenerated;
    }

    public int getNumberOfDriversAdded() {
        return numberOfDriversAdded;
    }

    public void setNumberOfDriversAdded(int numberOfDriversAdded) {
        this.numberOfDriversAdded = numberOfDriversAdded;
    }

    public int getNumberOfRoadsAdded() {
        return numberOfRoadsAdded;
    }

    public void setNumberOfRoadsAdded(int numberOfRoadsAdded) {
        this.numberOfRoadsAdded = numberOfRoadsAdded;
    }

    public int getNumberOfBatteriesAdded() {
        return numberOfBatteriesAdded;
    }

    public void setNumberOfBatteriesAdded(int numberOfBatteriesAdded) {
        this.numberOfBatteriesAdded = numberOfBatteriesAdded;
    }

    public int getNumberOfTyresAdded() {
        return numberOfTyresAdded;
    }

    public void setNumberOfTyresAdded(int numberOfTyresAdded) {
        this.numberOfTyresAdded = numberOfTyresAdded;
    }

    public int getNumberOfRoadtripsGenerated() {
        return numberOfRoadtripsGenerated;
    }

    public void setNumberOfRoadtripsGenerated(int numberOfRoadtripsGenerated) {
        this.numberOfRoadtripsGenerated = numberOfRoadtripsGenerated;
    }

    public int getNumberOfBatteriesChanged() {
        return numberOfBatteriesChanged;
    }

    public void setNumberOfBatteriesChanged(int numberOfBatteriesChanged) {
        this.numberOfBatteriesChanged = numberOfBatteriesChanged;
    }

    public int getNumberOfTyresChanged() {
        return numberOfTyresChanged;
    }

    public void setNumberOfTyresChanged(int numberOfTyresChanged) {
        this.numberOfTyresChanged = numberOfTyresChanged;
    }

    public int getNumberOfCarchecksMade() {
        return numberOfCarchecksMade;
    }

    public void setNumberOfCarchecksMade(int numberOfCarchecksMade) {
        this.numberOfCarchecksMade = numberOfCarchecksMade;
    }

    public int getNumberOfStatisticsGenerated() {
        return numberOfStatisticsGenerated;
    }

    public void setNumberOfStatisticsGenerated(int numberOfStatisticsGenerated) {
        this.numberOfStatisticsGenerated = numberOfStatisticsGenerated;
    }

    public int getNumberOfChargingBatteries() {
        return numberOfChargingBatteries;
    }

    public void setNumberOfChargingBatteries(int numberOfChargingBatteries) {
        this.numberOfChargingBatteries = numberOfChargingBatteries;
    }

    public int getNumberOfTyresInflated() {
        return numberOfTyresInflated;
    }

    public void setNumberOfTyresInflated(int numberOfTyresInflated) {
        this.numberOfTyresInflated = numberOfTyresInflated;
    }

    public int getNumberOfCoolingLiquidRefills() {
        return numberOfCoolingLiquidRefills;
    }

    public void setNumberOfCoolingLiquidRefills(int numberOfCoolingLiquidRefills) {
        this.numberOfCoolingLiquidRefills = numberOfCoolingLiquidRefills;
    }

    public int getNumberOfBrakingLiquidReffils() {
        return numberOfBrakingLiquidReffils;
    }

    public void setNumberOfBrakingLiquidReffils(int numberOfBrakingLiquidReffils) {
        this.numberOfBrakingLiquidReffils = numberOfBrakingLiquidReffils;
    }

    public int getNumberOfCSVFilesLoaded() {
        return numberOfCSVFilesLoaded;
    }

    public void setNumberOfCSVFilesLoaded(int numberOfCSVFilesLoaded) {
        this.numberOfCSVFilesLoaded = numberOfCSVFilesLoaded;
    }

    public int getNumberOfJSONFilesLoaded() {
        return numberOfJSONFilesLoaded;
    }

    public void setNumberOfJSONFilesLoaded(int numberOfJSONFilesLoaded) {
        this.numberOfJSONFilesLoaded = numberOfJSONFilesLoaded;
    }

    public int getNumberOfUsersRegistered() {
        return numberOfUsersRegistered;
    }

    public void setNumberOfUsersRegistered(int numberOfUsersRegistered) {
        this.numberOfUsersRegistered = numberOfUsersRegistered;
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
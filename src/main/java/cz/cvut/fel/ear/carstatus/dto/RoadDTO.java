package cz.cvut.fel.ear.carstatus.dto;

public class RoadDTO {
    private int id;
    private String startingPoint;
    private String endPoint;
    private int length;

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }
}

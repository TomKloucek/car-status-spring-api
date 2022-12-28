package cz.cvut.fel.ear.carstatus.interfaces;

public interface IFilter {
    public void setNext(IFilter handler);
    public void handleRequest();
}

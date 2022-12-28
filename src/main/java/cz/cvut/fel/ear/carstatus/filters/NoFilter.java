package cz.cvut.fel.ear.carstatus.filters;

import cz.cvut.fel.ear.carstatus.interfaces.IFilter;

public class NoFilter implements IFilter {

    private IFilter next;
    @Override
    public void setNext(IFilter handler) {

    }

    @Override
    public void handleRequest() {

    }
}

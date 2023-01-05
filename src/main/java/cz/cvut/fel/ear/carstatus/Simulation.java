package cz.cvut.fel.ear.carstatus;

import cz.cvut.fel.ear.carstatus.interfaces.ICommand;
import cz.cvut.fel.ear.carstatus.interfaces.ILoadSimulationFile;

import java.util.ArrayList;

public class Simulation {
    private ICommand command;
    private ILoadSimulationFile file;
    private SimulationData data;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}

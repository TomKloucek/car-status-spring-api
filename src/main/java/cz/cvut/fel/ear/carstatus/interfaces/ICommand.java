package cz.cvut.fel.ear.carstatus.interfaces;

import java.util.ArrayList;

public interface ICommand {
    public void execute(ArrayList<String> parameters);
}

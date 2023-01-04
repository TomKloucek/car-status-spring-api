package cz.cvut.fel.ear.carstatus.interfaces;

import org.springframework.data.util.Pair;

public interface ILoadSimulationFile {
    public Pair<Boolean, String> readSimulationFromFile(String fileContent);
}

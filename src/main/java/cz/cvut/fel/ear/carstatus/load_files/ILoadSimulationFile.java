package cz.cvut.fel.ear.carstatus.load_files;

import org.springframework.data.util.Pair;

public interface ILoadSimulationFile {
    public Pair<Boolean, String> readSimulationFromFile(String fileContent);
}
